package com.webproject.chatservice.controller;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.models.ChatRoom;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.ChatRoomService;
import com.webproject.chatservice.service.ChatMessageService;
import com.webproject.chatservice.utils.Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final Uploader uploader;

    @Autowired
    public ChatRoomController(ChatMessageService chatMessageService, ChatRoomService chatRoomService, Uploader uploader) {
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
        this.uploader = uploader;
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    public ChatRoom createChatRoom(@RequestBody ChatRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        requestDto.setUserId(userDetails.getUser().getId());
        ChatRoom chatRoom = chatRoomService.createChatRoom(requestDto);
        return chatRoom;
    }

    // 전체 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    // 채팅팅방 카테고리별 조회
    @GetMapping("/rooms/search/{category}")
    public List<ChatRoom> getChatRoomsByCategory(@PathVariable String category) {
        return chatRoomService.getAllChatRoomsByCategory(category);
    }

    // 채팅방 상세 조회
    @GetMapping("/rooms/{roomId}")
    public ChatRoom getEachChatRoom(@PathVariable Long roomId) {
        return chatRoomService.getEachChatRoom(roomId);
    }

    // 채팅방 내 메시지 전체 조회
    @GetMapping("/rooms/{roomId}/messages")
    public Page<ChatMessage> getEachChatRoomMessages(@PathVariable String roomId, @PageableDefault Pageable pageable) {
        return chatMessageService.getChatMessageByRoomId(roomId, pageable);
    }

}

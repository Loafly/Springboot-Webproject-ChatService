package com.webproject.chatservice.controller;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.models.ChatRoom;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.ChatRoomService;
import com.webproject.chatservice.service.ChatService;
import com.webproject.chatservice.utils.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin (origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final Uploader uploader;

    @GetMapping("/rooms")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @PostMapping("/rooms")
    public ChatRoom createChatRoom(@RequestParam("data") MultipartFile file, @RequestParam("chatRoomName")String chatRoomName , ChatRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String chatRoomUrl = uploader.upload(file, "static");
        requestDto.setChatRoomImg(chatRoomUrl);
        requestDto.setChatRoomName(chatRoomName);
        requestDto.setUserId(userDetails.getUser().getId());
        ChatRoom chatRoom = chatRoomService.createChatRoom(requestDto);
        return chatRoom;
    }

    @GetMapping("/rooms/{roomId}")
    public ChatRoom getEachChatRoom(@PathVariable Long roomId) {
        return chatRoomService.getEachChatRoom(roomId);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public Page<ChatMessage> getEachChatRoomMessages(@PathVariable String roomId, @PageableDefault Pageable pageable) {
        return chatService.getChatMessageByRoomId(roomId, pageable);
    }

}

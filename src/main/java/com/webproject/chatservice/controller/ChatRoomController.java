package com.webproject.chatservice.controller;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.models.ChatRoom;
import com.webproject.chatservice.service.ChatRoomService;
import com.webproject.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @GetMapping("/rooms")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @PostMapping("/rooms")
    public ChatRoom createChatRoom(@RequestBody ChatRoomRequestDto requestDto) {
        requestDto.getChatRoomName();
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

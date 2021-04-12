package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.models.ChatRoom;
import com.webproject.chatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;

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

}

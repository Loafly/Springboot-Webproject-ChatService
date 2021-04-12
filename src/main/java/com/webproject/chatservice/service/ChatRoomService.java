package com.webproject.chatservice.service;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.models.ChatRoom;
import com.webproject.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAllByOrderByCreatedAtDesc();
    }

    public ChatRoom createChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = new ChatRoom(requestDto);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public ChatRoom getEachChatRoom(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾는 채팅방이 존재하지 않습니다.")
        );
        return chatRoom;
    }

}

package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String chatRoomName;

    public ChatRoom(ChatRoomRequestDto requestDto) {
        this.chatRoomName = requestDto.getChatRoomName();
    }

}

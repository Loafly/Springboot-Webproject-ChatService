package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;

import javax.persistence.*;

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

    @Column
    private String chatRoomImg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public ChatRoom(ChatRoomRequestDto requestDto, UserService userService) {
        this.chatRoomName = requestDto.getChatRoomName();
        this.chatRoomImg = requestDto.getChatRoomImg();
        this.user = userService.findById(requestDto.getUserId());
    }

}

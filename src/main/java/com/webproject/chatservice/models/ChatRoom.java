package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.ChatRoomRequestDto;
import com.webproject.chatservice.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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

    @ElementCollection
    private Set<String> category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public ChatRoom(ChatRoomRequestDto requestDto, UserService userService) {
        this.chatRoomName = requestDto.getChatRoomName();
        this.chatRoomImg = requestDto.getChatRoomImg();
        this.category = requestDto.getCategory();
        this.user = userService.findById(requestDto.getUserId());
    }

}

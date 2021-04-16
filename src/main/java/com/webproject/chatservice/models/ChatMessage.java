package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.ChatMessageRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String roomId;

    @Column
    private Long userId;

    @Column
    private String sender;

    @Column
    private String senderEmail;

    @Column
    private String message;

    @Column
    private String createdAt;

    @Builder
    public ChatMessage(MessageType type, String roomId, Long userId, String sender, String senderEmail,String message, String createdAt) {
        this.type = type;
        this.roomId = roomId;
        this.userId = userId;
        this.sender = sender;
        this.senderEmail = senderEmail;
        this.message = message;
        this.createdAt = createdAt;
    }

    @Builder
    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto) {
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
        this.userId = chatMessageRequestDto.getUserId();
        this.sender = chatMessageRequestDto.getSender();
        this.senderEmail = chatMessageRequestDto.getSenderEmail();
        this.message = chatMessageRequestDto.getMessage();
        this.createdAt = chatMessageRequestDto.getCreatedAt();
    }
}
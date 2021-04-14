package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.ChatMessageRequestDto;
import lombok.*;

import javax.persistence.*;

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
    private String sender;

    @Column
    private String senderEmail;

    @Column
    private String message;

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String senderEmail,String message) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.senderEmail = senderEmail;
        this.message = message;
    }

    @Builder
    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto) {
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
        this.sender = chatMessageRequestDto.getSender();
        this.senderEmail = chatMessageRequestDto.getSenderEmail();
        this.message = chatMessageRequestDto.getMessage();
    }
}
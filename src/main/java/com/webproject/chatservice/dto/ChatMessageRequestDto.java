package com.webproject.chatservice.dto;

import com.webproject.chatservice.models.ChatMessage;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageRequestDto {

    private ChatMessage.MessageType type;
    private String roomId;
    private String sender;
    private String senderEmail;
    private String message;
    private String createdAt;

}

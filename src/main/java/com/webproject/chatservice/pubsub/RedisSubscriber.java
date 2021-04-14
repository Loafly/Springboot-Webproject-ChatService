package com.webproject.chatservice.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    // Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber 가 해당 메시지를 받아 처리한다.
    public void sendMessage(String publishMessage) {
        try {
            // ChatMessage 객채로 맵핑
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송
            messagingTemplate.convertAndSend("/sub/api/chat/rooms/" + chatMessage.getRoomId(), chatMessage);

            // 여기가 바로 채팅 메시지 value 값입니다!!
//            ChatMessage message = new ChatMessage();
//            message.setType(chatMessage.getType());
//            message.setRoomId(chatMessage.getRoomId());
//            message.setSender(chatMessage.getSender());
//            message.setSenderEmail(chatMessage.getSenderEmail());
//            message.setMessage(chatMessage.getMessage());
//            chatMessageRepository.save(message);
            System.out.println("hello world");
            System.out.println("chatMessage.getMessage() = " + chatMessage.getMessage());
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}

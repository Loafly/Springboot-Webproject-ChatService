package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.ChatMessageRequestDto;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.service.ChatService;
import com.webproject.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ChatService chatService;

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatMessageRequestDto messageRequestDto, @Header("token") String token) {
//    public void message(@RequestBody ChatMessageRequestDto messageRequestDto) {
        // 로그인 회원 정보로 대화명 설정

        User user = jwtTokenProvider.getAuthenticationUser(token);

//        message.setSender(userDetails.getUsername());
        messageRequestDto.setSender(user.getUsername());

        // 로그인 회원 정보로 유저 이메일 설정
//        message.setSenderEmail(userDetails.getUser().getEmail());
        messageRequestDto.setSenderEmail(user.getEmail());

        System.out.println(messageRequestDto.getType());
        System.out.println(messageRequestDto.getRoomId());
        System.out.println(messageRequestDto.getSender());
        System.out.println(messageRequestDto.getSenderEmail());
        System.out.println(messageRequestDto.getMessage());

        ChatMessage chatMessage = new ChatMessage(messageRequestDto);
        chatService.sendChatMessage(chatMessage);
        chatService.save(chatMessage);
    }

//    현재 사용자 아이디 가져오기 연습
//    @GetMapping("/username")
//    public String name(@RequestHeader("token") String token) {
//        String email =  jwtTokenProvider.getUserPk(token);
//        Optional<User> user = userService.findByEmail(email);
//        return user.get().getUsername();
//    }

}

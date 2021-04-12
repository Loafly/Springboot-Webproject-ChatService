package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.ChatMessage;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.service.ChatService;
import com.webproject.chatservice.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ChatService chatService;

    @MessageMapping("/message")
    public void message(ChatMessage message, @RequestHeader("token") String token) {
        // 로그인 회원 정보로 대화명 설정
        String email = jwtTokenProvider.getUserPk(token);
        Optional<User> user = userService.findByEmail(email);
        String sender = user.get().getUsername();
        message.setSender(sender);

        chatService.sendChatMessage(message);
    }

//    현재 사용자 아이디 가져오기 연습
//    @GetMapping("/username")
//    public String name(@RequestHeader("token") String token) {
//        String email =  jwtTokenProvider.getUserPk(token);
//        Optional<User> user = userService.findByEmail(email);
//        return user.get().getUsername();
//    }

}

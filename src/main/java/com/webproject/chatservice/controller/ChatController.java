package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.ChatMessageRequestDto;
import com.webproject.chatservice.models.ChatMessage;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatService chatService;

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatMessageRequestDto messageRequestDto, @Header("token") String token) {
        // 로그인 회원 정보로 대화명 설정
        User user = jwtTokenProvider.getAuthenticationUser(token);

        messageRequestDto.setSender(user.getUsername());

        // 로그인 회원 정보로 유저 이메일 설정
        messageRequestDto.setSenderEmail(user.getEmail());
        messageRequestDto.setUserId(user.getId());

        // 메시지 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        
        messageRequestDto.setCreatedAt(dateResult);
        
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

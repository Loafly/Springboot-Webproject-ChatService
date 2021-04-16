package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class KakaoController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public KakaoController(UserService userService, JwtTokenProvider jwtTokenProvider)
    {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public ModelAndView kakaoLogin(String code, HttpServletResponse response) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        System.out.println("callback 함수 호출");
        String token = userService.kakaoLogin(code);

        response.addHeader("token",token);

        return new ModelAndView("redirect:http://gaemangtalk.site/login/kakao");
    }
}

package com.webproject.chatservice.controller;

import com.google.gson.JsonObject;
import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.dto.UserSignupRequestDto;
import com.webproject.chatservice.handler.CustomMessageResponse;
import com.webproject.chatservice.kakao.KakaoOAuth2;
import com.webproject.chatservice.kakao.KakaoUserInfo;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.ChatService;
import com.webproject.chatservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuth2 kakaoOAuth2;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, KakaoOAuth2 kakaoOAuth2)
    {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoOAuth2 = kakaoOAuth2;
    }

    //회원 조회
    @GetMapping("/api/users")
    public List<User> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.findAll();
    }

    //소셜 로그인 시 Token으로 User 정보 보내주기
    @GetMapping("/api/user/getUserInfo")
//    public Object getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails){
    public Object getUserName(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        System.out.println("token = " + token);
//        System.out.println("email = " + userDetails.getUser().getEmail());
//
        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("username", userDetails.getUser().getUsername());
//        jsonObj.addProperty("userid", userDetails.getUser().getId());

        return ResponseEntity.ok().body(jsonObj.toString());
    }

    //로그인
    @PostMapping("/api/user/login")
    public Object loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto)
    {
        try{
            User user = userService.loginValidCheck(userLoginRequestDto);

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("token", jwtTokenProvider.createToken(user.getId()));
            jsonObj.addProperty("username", user.getUsername());
            jsonObj.addProperty("userid", user.getId());

            return ResponseEntity.ok().body(jsonObj.toString());
        }
        catch (Exception ignore)
        {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

//    카카오 로그인
//    프론트엔드에서 처리 후 카카오 토큰을 백으로 넘겨 주어 JWT token, username, userid 반환
    @PostMapping("/api/user/kakaoLogin")
    public Object loginUser(@RequestBody Map<String, Object> param)
    {
        try{
            return ResponseEntity.ok().body(userService.kakaoLogin(param.get("kakaoToken").toString()));
        }
        catch (Exception ignore)
        {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    //회원 가입
    @PostMapping("/api/user/signup")
    public Object registerUsers(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto){
        try{
            userService.signupValidCheck(userSignupRequestDto.getEmail());
            User user = new User(userSignupRequestDto);
            return userService.registerUser(user);
        }
        catch (Exception ignore)
        {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    //회원 가입시 이메일 중복체크
    @PostMapping("/api/user/signup/emailCheck")
    public Object validCheckEmail(@RequestBody Map<String, Object> param){
        try{
            userService.signupValidCheck(param.get("email").toString());
            CustomMessageResponse customMessageResponse = new CustomMessageResponse("사용 가능한 Email입니다.",HttpStatus.OK.value());
            return customMessageResponse.SendResponse();
        }
        catch (Exception ignore)
        {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    //회원 수정
    @PutMapping("/api/user/update")
    public User updateUsers(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto){
        return null;
    }

    //회원 삭제
    @PutMapping("/api/user/delete")
    public Long deleteUsers(@RequestBody Map<String, Object> param){
        return userService.deleteUser((Long) param.get("id"));
    }

    //비밀번호 찾기
    @PostMapping("/api/user/findPassword")
    public Object findPasswordByEamil(@RequestBody Map<String, Object> param){
        try
        {
            int CertificationNumber = userService.findPasswordByEamil(param.get("email").toString());

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("CertificationNumber", Integer.toString(CertificationNumber));

            return ResponseEntity.ok().body(jsonObj.toString());
        }
        catch (Exception ignore)
        {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    //비밀번호 변경
    @PutMapping("/api/user/changePassword")
    public Long updateUserPassword(@RequestBody Map<String, Object> param){
        String email = param.get("email").toString();
        String password = param.get("password").toString();

        return userService.updateUserPassword(email,password);
    }

    // 마이페이지
}
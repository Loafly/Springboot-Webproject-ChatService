package com.webproject.chatservice.controller;

import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.dto.UserSignupRequestDto;
import com.webproject.chatservice.handler.CustomMessageResponse;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider)
    {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //회원 조회
    @GetMapping("/api/users")
    public List<User> getUser(){
        return userService.findAll();
    }

    //로그인
    @PostMapping("/api/user/login")
    public Object loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto)
    {
        try{
            User user = userService.loginValidCheck(userLoginRequestDto);
            return ResponseEntity.ok().body("token : " + jwtTokenProvider.createToken(user.getEmail()));
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
}
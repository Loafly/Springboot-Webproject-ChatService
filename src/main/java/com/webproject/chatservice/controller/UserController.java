package com.webproject.chatservice.controller;

import com.google.gson.JsonObject;
import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.dto.UserSignupRequestDto;
import com.webproject.chatservice.handler.CustomMessageResponse;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.UserService;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<User> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("email = " + userDetails.getUser().getEmail());
        System.out.println("username = " + userDetails.getUser().getUsername());
        System.out.println("password = " + userDetails.getUser().getPassword());
        System.out.println("id = " + userDetails.getUser().getId());
        System.out.println("role = " + userDetails.getUser().getRole());
        return userService.findAll();
    }

    //로그인
    @PostMapping("/api/user/login")
    public Object loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto)
    {
        try{
            User user = userService.loginValidCheck(userLoginRequestDto);

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("token", jwtTokenProvider.createToken(user.getEmail()));
            jsonObj.addProperty("username", user.getUsername());

            return ResponseEntity.ok().body(jsonObj.toString());
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
}
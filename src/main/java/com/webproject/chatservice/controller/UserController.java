package com.webproject.chatservice.controller;

import com.webproject.chatservice.dto.UserRequestDto;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    //GitTest용 주석입니다

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    //회원 조회
    @GetMapping("/api/users")
    public List<User> getUser(){
        return userService.findAll();
    }

    //회원 가입
    @PostMapping("/api/user/signup")
    public Long registerUsers(@Valid @RequestBody UserRequestDto userRequestDto){
        User user = new User(userRequestDto);
        return userService.registerUser(user);
    }

    //회원 수정
    @PutMapping("/api/user/update")
    public User updateUsers(@Valid @RequestBody UserRequestDto userRequestDto){
        return null;
    }
}
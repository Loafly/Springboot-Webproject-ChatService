package com.webproject.chatservice.controller;

import com.google.gson.JsonObject;
import com.webproject.chatservice.config.JwtTokenProvider;
import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.dto.UserProfileRequestDto;
import com.webproject.chatservice.dto.UserSignupRequestDto;
import com.webproject.chatservice.handler.CustomMessageResponse;
import com.webproject.chatservice.kakao.KakaoOAuth2;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.UserService;

//import com.webproject.chatservice.utils.S3Uploader;
import com.webproject.chatservice.utils.Uploader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuth2 kakaoOAuth2;
    private final Uploader uploader;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, KakaoOAuth2 kakaoOAuth2, Uploader uploader)
    {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoOAuth2 = kakaoOAuth2;
        this.uploader = uploader;
    }

    //회원 조회
    @GetMapping("/api/users")
    public List<User> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.findAll();
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
            JsonObject jsonObj = userService.kakaoLogin(param.get("kakaoToken").toString());
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

    //회원 삭제
    @DeleteMapping("/api/user/delete")
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

    // 마이페이지 프로필 조회
    // token 키 값으로 Header 에 실어주시면 된다!!
    @GetMapping("/api/user/profile")
    public User getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.findByUsername(userDetails.getUsername());
    }

    // 마이페이지 프로필 수정
    // username, email, profileurl 만 바꿀 수 있도록 함
    @PutMapping("api/user/profile/{userId}")
    public User updateMyProfile(@PathVariable Long userId, @RequestBody UserProfileRequestDto userProfileRequestDto) {
        return userService.myProfileUpdate(userId, userProfileRequestDto);
    }

    // 마이페이지 프로필 사진 수정
    @PutMapping("/api/user/profile/{userId}/img")
    public User upload(@RequestParam("data") MultipartFile file, @PathVariable Long userId) throws IOException {
        String profileUrl = uploader.upload(file, "static");
        return userService.myProfileUrlUpdate(userId,profileUrl);
//        return profileUrl;
    }

}
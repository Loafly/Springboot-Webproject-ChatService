package com.webproject.chatservice.dto;


import com.webproject.chatservice.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDto {

    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private Long kakaoId;
    private String profileUrl;

}

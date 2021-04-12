package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.dto.UserSignupRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(UserSignupRequestDto userSignupRequestDto){
        this.username = userSignupRequestDto.getUsername();
        this.password = userSignupRequestDto.getPassword();
        this.email = userSignupRequestDto.getEmail();
        this.role = UserRole.USER;
    }
}

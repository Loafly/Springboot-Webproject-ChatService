package com.webproject.chatservice.models;

import com.webproject.chatservice.dto.UserRequestDto;
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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        this.email = userRequestDto.getEmail();
        this.role =UserRole.USER;
    }
}

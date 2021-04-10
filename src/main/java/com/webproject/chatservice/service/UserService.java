package com.webproject.chatservice.service;

import com.webproject.chatservice.models.User;
import com.webproject.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Long registerUser(User user){
        userRepository.save(user);
        return user.getId();
    }

}

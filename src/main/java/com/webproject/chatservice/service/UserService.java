package com.webproject.chatservice.service;

import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.repository.UserRepository;
import com.webproject.chatservice.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }

    public Long registerUser(User user){
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return user.getId();
    }

    public void signupValidCheck(String Email){
        if (userRepository.findByEmail(Email).isPresent())
        {
            throw new IllegalArgumentException("해당 이메일은 이미 가입된 회원이 있습니다.");
        }
    }

    public User loginValidCheck(UserLoginRequestDto userLoginRequestDto){
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return user;
    }

    public int findPasswordByEamil(String email) {
        MailUtil mailUtil = new MailUtil();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        try{
            return mailUtil.sendMail(user);
        }
        catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    @Transactional
    public Long updateUserPassword(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        user.setPassword(passwordEncoder.encode(password));
        return user.getId();
    }


    public Long deleteUser(Long id)
    {
        userRepository.deleteById(id);
        return id;
    }
}

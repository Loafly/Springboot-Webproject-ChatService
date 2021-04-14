package com.webproject.chatservice.service;

import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.handler.MailHandler;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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

    public int findPasswordByEamil(String tomail){
        Random r = new Random();
        int dice = r.nextInt(157211)+48271;

        String setfrom = "skatjr30@naver.com";
        String title = "비밀번호 찾기 인증 이메일 입니다.";    //제목
        String content =

                System.getProperty("line.separator")+

                        System.getProperty("line.separator")+

                        "안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"

                        +System.getProperty("line.separator")+

                        System.getProperty("line.separator")+

                        "비밀번호 찾기 인증번호는 " +dice+ " 입니다. "

                        +System.getProperty("line.separator")+

                        System.getProperty("line.separator")+

                        "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            // 받는 사람
            mailHandler.setTo(tomail);
            // 보내는 사람
            mailHandler.setFrom(setfrom);
            // 제목
            mailHandler.setSubject(title);
            // HTML Layout
//            String htmlContent = "<p>" + mailDto.getMessage() +"<p> <img src='cid:sample-img'>";
//            mailHandler.setText(htmlContent, true);
//            // 첨부 파일
//            mailHandler.setAttach("newTest.txt", "static/originTest.txt");
//            // 이미지 삽입
//            mailHandler.setInline("sample-img", "static/sample1.jpg");

            // 내용
            mailHandler.setText(content,true);

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return dice;
    }

    public Long deleteUser(Long id)
    {
        userRepository.deleteById(id);
        return id;
    }
}

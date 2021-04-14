package com.webproject.chatservice.service;

import com.webproject.chatservice.dto.UserLoginRequestDto;
import com.webproject.chatservice.models.User;
import com.webproject.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public Long findPasswordByEamil(String tomail){
//        Random r = new Random();
//        int dice = r.nextInt(157211)+48271;
//
//        String setfrom = "dlgkstjq623@gmail.com";
//        String title = "비밀번호 찾기 인증 이메일 입니다.";    //제목
//        String content =
//
//                System.getProperty("line.separator")+
//
//                        System.getProperty("line.separator")+
//
//                        "안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"
//
//                        +System.getProperty("line.separator")+
//
//                        System.getProperty("line.separator")+
//
//                        "비밀번호 찾기 인증번호는 " +dice+ " 입니다. "
//
//                        +System.getProperty("line.separator")+
//
//                        System.getProperty("line.separator")+
//
//                        "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용
//
//        try {
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
//
//            messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
//            messageHelper.setTo(tomail); // 받는사람 이메일
//            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
//            messageHelper.setText(content); // 메일 내용
//
//            mailSender.send(message);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        response_email.setContentType("text/html; charset=UTF-8");
//        PrintWriter out_email = response_email.getWriter();
//        out_email.println("<script>alert('이메일이 발송되었습니다. 인증번호를 입력해주세요.');</script>");
//        out_email.flush();
//
        return null;
    }

    public Long deleteUser(Long id)
    {
        userRepository.deleteById(id);
        return id;
    }
}

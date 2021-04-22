Springboot-Webproject-ChatService-개망톡
=====================

참여인원 : 백앤드 2명 (김남석, 채수연) / 프론트 2명 (전재민, 조윤경)


</br>

프로젝트 API 서버 주소:  
프로젝트 웹 서버 주소:


</br>

개발 언어
---------
- Backend: Java 8
- Frontend: React


</br>

개발 환경
---------
- Java: JDK 1.8.0
- IDE: IntelliJ IDEA 2020.3.3 x64
- Build Management: Gradle
- Framework: SpringBoot
> - ORM: Spring Data JPA
> - Sub-Framework: Spring security
> - Other Libraries: Websocket, AWS, Lombok

- DBMS
> - MySql : 데이터베이스
> - Redis : 메시징큐 기능 사용
> - S3 : 이미지 스토리지

</br>

폴더 구조
---------
```
main
java
com.clone.daangnclone
│  │  
├─ config
│  └─ EmbeddedRedisConfig.java
│  └─ JwtAuthenticationFilter.java
│  └─ JwtTokenProvider.java
│  └─ RedisConfig.java
│  └─ WebMVCConfig.java
│  └─ WebSecurityConfig.java
│  └─ WebSocketConfig.java
│  │  
├─ controller
│  └─ ChatMessageController.java
│  └─ ChatRoomController.java
│  └─ S3Controller.java
│  └─ UserController.java
│  │  
├─ dto
│  └─ ChatMessageRequestDto.java
│  └─ ChatRoomRequestDto.java
│  └─ UserLoginRequestDto.java
│  └─ UserProfileRequestDto.java
│  └─ UserSignupRequestDto.java
│  │  
├─ error
│  └─ ErrorCode.java
│  └─ ErrorResponse.java
│  └─ ExceptionController.java
│  │  
├─ handler
│  └─ CustomMessageResponse.java
│  └─ StompHandler.java
│  │  
├─ kakao
│  └─ KakaoOAuth2.java
│  └─ KakaoUserInfo.java
│  │ 
├─ models
│  └─ ChatMessage.java
│  └─ ChatRoom.java
│  └─ Timestamped.java
│  └─ User.java
│  └─ UserDetailsImpl.java
│  └─ UserRole.java
│  │ 
├─ pubsub
│  └─ RedisSubscriber.java
│  │ 
├─ repository
│  └─ ChatMessageRepository.java
│  └─ ChatRoomRepository.java
│  └─ UserRepository.java
│  │ 
├─ service
│  └─ ChatRoomService.java
│  └─ ChatMessageService.java
│  └─ UserDetailsServiceImpl.java
│  └─ UserService.java
│  │ 
├─ utils
│  └─ CORSFilter.java
│  └─ MailUtil.java
│  └─ S3Uploader.java
│  └─ Uploader.java
│  │ 
└─  ChatserviceApplication.java
```


</br>

주요 기능
--------
- 회원가입 페이지
> - 이메일 중복 확인
> - 닉네임 유효성 검사
> - 비밀번호 일치 확인

- 비밀번호 찾기 페이지
> - 이메일 입력을 통한 인증번호 발송
> - 인증번호 입력
> - 인증 성공 시 비밀번호 변경 페이지 이동

- 로그인 페이지
> - 기본 로그인
> - 소셜 로그인
> - 회원가입 페이지 이동
> - 비밀번호 찾기 페이지 이동

- 마이프로필 페이지
> - 유저 프로필 사진 : 사진 파일을 업로드 하면 S3 이미지 스토리지에 저장한 후 URL 반환
> - 유저 정보 수정 : 유저 프로필 사진, 유저 닉네임 수정


- 채팅 페이지
> - 채팅방 생성하기 : 채팅방 이름, 대표 이미지, 카테고리 설정 가능
> - 채팅방 조회하기 : 이름, 이미지, 카테고리에 추가하여 생성자 프로필 정보 조회
> - 채팅방 카테고리별 조회하기 : 채팅방 카테고리에 따라 해당 카테고리를 가진 채팅방 목록 조회
> - 채팅방 입장
> > - 해당 채팅방에서 기존에 대화했던 대화 내용 전체 조회
> > - 채팅방 목록 중 한 곳을 누르면 username + 님이 방에 입장했습니다 메시지 반환
> > - 해당 채팅방 Topic이 구독되어, 해당 채팅방으로 pub 되는 메세지를 모두 웹소켓을 통해 받아 볼 수 있음
> - 채팅 메시지 작성 : 채팅 메시지를 작성해 pub 하면 해당 채팅방을 구독 중인 이용자 모두에게 sub 메시지 반환
> - 채팅방 퇴장 : 다른 채팅방으로 옮겨갈 시 username + 님이 퇴장했습니다 메시지 반환


Entity Table Structure
----------------------


</br>

API Structure
-------------


</br>

# 기능 상세 소개

---------------

채팅방 관련 기능
---------


</br>

채팅 메시지 관련 기능
-----------------


</br>

회원 가입 기능
----------------

###UserSignupRequestDto
```java
package com.webproject.chatservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequestDto {

    // 한글 영어 - _ 숫자 로 변경 필요
    @NotNull
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣-_/]{3,10}$", message = "3~10자리의 '-','_', 한글, 알파벳만 사용 가능합니다.")
    private String username;

    @NotNull
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotNull
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;

}
```
- @Pattern : 정규식을 이용 하여 3 ~ 10 자리의 '알파벳, 숫자, 한글, -, _, /' 이외 입력 시 Exception 처리
- @NotNull : 받아온 JSON 내용에 해당하는 key가 없을 경우 Exception 처리
- @NotBlank : 빈값, 공백인 경우 Exception 처리
- @Email : 이메일 형식이 아닌 경우 Exception 처리
- @Valid : Controller단에서 위와같은 Exception 처리가 있는지 없는지 확인

###UserController
```java
    //회원 가입시 이메일 중복체크
    @PostMapping("/api/user/signup/emailCheck")
    public Object validCheckEmail(@RequestBody Map<String, Object> param) {
        try {
            userService.signupValidCheck(param.get("email").toString());
            CustomMessageResponse customMessageResponse = new CustomMessageResponse("사용 가능한 Email입니다.",HttpStatus.OK.value());
            return customMessageResponse.SendResponse();
        } catch (Exception ignore) {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }
```

###UserService
```java
    public void signupValidCheck(String Email){
        if (userRepository.findByEmail(Email).isPresent()) {
            throw new IllegalArgumentException("해당 이메일은 이미 가입된 회원이 있습니다.");
        }
    }
```

- /api/user/signup/emailCheck : 중복 확인 버튼 클릭 시 발동 하며 Service의 signupValidCheck함수를 통해 이메일 중복 검사를 진행합니다.


비밀번호 찾기 기능
----------------
###UserController
```java
    //비밀번호 찾기
    @PostMapping("/api/user/findPassword")
    public Object findPasswordByEamil(@RequestBody Map<String, Object> param){
        try {
            int CertificationNumber = userService.findPasswordByEamil(param.get("email").toString());
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("CertificationNumber", Integer.toString(CertificationNumber));
            return ResponseEntity.ok().body(jsonObj.toString());
        } catch (Exception ignore) {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    // 비밀번호 변경
    @PutMapping("/api/user/changePassword")
    public Long updateUserPassword(@RequestBody Map<String, Object> param) {
            String email = param.get("email").toString();
            String password = param.get("password").toString();
            return userService.updateUserPassword(email,password);
            }
```

###UserService
```java
    public int findPasswordByEamil(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        try {
            return mailUtil.sendMail(user);
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional
    public Long updateUserPassword(String email, String password) {
            User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
            user.setPassword(passwordEncoder.encode(password));
            return user.getId();
            }
```

###MailUtil
```java
package com.webproject.chatservice.utils;

import com.webproject.chatservice.models.User;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
//메일을 보낼 클래스
public class MailUtil {
    @Value("${spring.mail.host}")
    private String hostSMTP;

    @Value("${spring.mail.username}")
    private String hostSMTPid;

    @Value("${spring.mail.password}")
    private String hostSMTPpw;

    public int sendMail(User user) throws Exception {
        Random r = new Random();
        int dice = r.nextInt(157211)+48271;

        //Mail Server 설정
        String charSet = "utf-8";
        //보내는 사람
        String fromEmail = this.hostSMTPid + "@naver.com";
        String fromName = "gaemangtalk";

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

        //email 전송
        String mailRecipient = user.getEmail(); //받는 사람 이메일 주소
        try {
            //객체 선언
            HtmlEmail mail = new HtmlEmail();
            mail.setDebug(true);
            mail.setCharset(charSet);
            mail.setSSLOnConnect(true); //SSL 사용
            mail.setHostName(hostSMTP);
            mail.setSmtpPort(465); //SMTP 포트 번호
            mail.setAuthentication(hostSMTPid, hostSMTPpw);
            mail.setStartTLSEnabled(true); //TLS 사용
            mail.addTo(mailRecipient, charSet);
            mail.setFrom(fromEmail, fromName, charSet);
            mail.setSubject(title);
            mail.setHtmlMsg(content);
            mail.send();

            return dice;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
```

- /api/user/findPassword - userService.findPasswordByEamil에서 가입된 이메일이 있을경우 MailUtil이 동작합니다.
  MailUtil에서는 랜덤 함수를 통해 인증번호를 생성하고 미리SMTP허용한 이메일 정보를 통해 메일 발송 후 Client에 인증번호 발송하여 비교할 수 있도록 합니다.
- /api/user/changePassword - userService.updateUserPassword를 통해 변경 할 비밀번호변경

</br>

로그인 기능
--------------------

###JwtTokenProvider
```java
package com.webproject.chatservice.config;

import com.webproject.chatservice.models.User;
import com.webproject.chatservice.models.UserDetailsImpl;
import com.webproject.chatservice.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "webfirewood";

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(Long userPk) {
        String stringUserPk = userPk.toString();
        Claims claims = Jwts.claims().setSubject(stringUserPk); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserById(Long.parseLong(this.getUserPk(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 User 가져오기
    public User getAuthenticationUser(String token){
        UserDetailsImpl userDetails = userDetailsServiceImpl.loadUserById(Long.parseLong(this.getUserPk(token)));
        return userDetails.getUser();
    }

    // JWT 토큰에서 username 조회
    public String getAuthenticationUsername(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserById(Long.parseLong(this.getUserPk(token)));
        return userDetails.getUsername();
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
```

###UserController
```java
    //로그인
    @PostMapping("/api/user/login")
    public Object loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            User user = userService.loginValidCheck(userLoginRequestDto);
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("token", jwtTokenProvider.createToken(user.getId()));
            return ResponseEntity.ok().body(jsonObj.toString());
        } catch (Exception ignore) {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
        }
    }

    // 카카오 로그인
    // 프론트엔드에서 처리 후 카카오 토큰을 백으로 넘겨 주어 JWT token, username, userid 반환
    @PostMapping("/api/user/kakaoLogin")
    public Object loginUser(@RequestBody Map<String, Object> param) {
            try {
            JsonObject jsonObj = userService.kakaoLogin(param.get("kakaoToken").toString());
            return ResponseEntity.ok().body(jsonObj.toString());
            } catch (Exception ignore) {
            CustomMessageResponse customMessageResponse = new CustomMessageResponse(ignore.getMessage(),HttpStatus.BAD_REQUEST.value());
            return customMessageResponse.SendResponse();
            }
        }
```

###UserService
```java
    public User loginValidCheck(UserLoginRequestDto userLoginRequestDto){
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return user;
    }

    public JsonObject kakaoLogin(String accessToken) {
            // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
            KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(accessToken);
            Long kakaoId = userInfo.getId();
            String nickname = userInfo.getNickname();
            String email = userInfo.getEmail();
    
            // DB 에 중복된 Kakao Id 가 있는지 확인
            User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);
    
            // 카카오 정보로 회원가입
            if (kakaoUser == null) {
            // 카카오 이메일과 동일한 이메일을 가진 회원이 있는지 확인
            User sameEmailUser = null;
    
            if (email != null) {
            sameEmailUser = userRepository.findByEmail(email).orElse(null);
            }
            if (sameEmailUser != null) {
            kakaoUser = sameEmailUser;
            // 카카오 이메일과 동일한 이메일 회원이 있는 경우
            // 카카오 Id 를 회원정보에 저장
            kakaoUser.setKakaoId(kakaoId);
            userRepository.save(kakaoUser);
            } else {
                // 카카오 정보로 회원가입
                // username = 카카오 nickname
                String username = nickname;
                // password = 카카오 Id + ADMIN TOKEN
                String password = kakaoId + ADMIN_TOKEN;
                // 패스워드 인코딩
                String encodedPassword = passwordEncoder.encode(password);
                // ROLE = 사용자
                UserRole role = UserRole.USER;
        
                if (email != null) {
                kakaoUser = new User(username, encodedPassword, email, role, kakaoId);
                } else {
                kakaoUser = new User(username, encodedPassword, role, kakaoId);
                }
                userRepository.save(kakaoUser);
                }
            }
    
            // 스프링 시큐리티 통해 로그인 처리
            UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("token", jwtTokenProvider.createToken(userDetails.getUser().getId()));
            return jsonObj;
        }
```
- /api/user/login : userService.loginValidCheck을 통해 현재 회원가입이 되어있는 사용자가 맞는지 비밀번호는 일치하는지 비교 후 jwtTokenProvider.createToken(user.getId()) 을 통해 Server에서 관리하는 Token을 생성 후 ResponseBody에 실어 보내줍니다.
- /api/user/kakaoLogin : Client에서 kakao로그인을 하여 발급받은 kakaoToken을 받아 kakaoId, nickname, Email을 통해 db에 저장합니다. 만약 db에 일치하는 Email이 있을 경우 kakaoId만 업데이트 시켜줍니다.
  이후 Server에서 현재 로그인 상태를 확인 할 수 있도록 Server에서 관리 하는 Token을 새로 생성하여 ResponseBody에 실어 보내줍니다.
  </br>

보안관련 기능
--------------------
###WebSecurityConfig
```java
package com.webproject.chatservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WebSecurityConfig (JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // login 없이 허용
                .antMatchers("/chatting/**").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/kakaoLogin").permitAll()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/signup/emailCheck").permitAll()
                .antMatchers("/api/user/findPassword").permitAll()
                .antMatchers("/api/user/changePassword").permitAll()
                .antMatchers("/api/chat/message").permitAll()

                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}
```

###JwtAuthenticationFilter
```java
package com.webproject.chatservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            if (((HttpServletRequest) request).getMethod().toString().equals("OPTIONS")) {
                ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
                ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "*");
                ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "*");
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        chain.doFilter(request, response);
    }
}
```
- WebSecurityConfig.configure안에 .addFilterBefore을 통해 Security가 동작 하기 전  JwtAuthenticationFilter에 을 먼저 실행합니다.
- JwtAuthenticationFilter.doFilter를 Override 하여 Server에서 생성 한 Token이 헤더에 실려 올 경우 Authentication을 Context에 넣어 주었습니다.
- 헤더에 Token이 실려오지 않을 경우 Context에 있는 Authentication로 변경 해 주었으며 CORS 호출 시에는 GET, POST 등 요청할 경우 OPTIONS을 먼저 보내 가도 되는곳인지 확인하는 과정이 있어 OPTIONS을 분기처리 하였습니다.



마이 페이지 관련 기능
-----------------


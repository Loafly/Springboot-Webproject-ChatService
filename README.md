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
│  └─ ChatController.java
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
│  └─ ChatService.java
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

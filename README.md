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


- 로그인 페이지


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

회원 가입 관련 기능
----------------


</br>

로그인 및 보안 관련 기능
--------------------


</br>

마이 페이지 관련 기능
-----------------


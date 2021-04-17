//package com.webproject.chatservice.utils;
//
////import com.amazonaws.services.s3.AmazonS3Client;
////import com.amazonaws.services.s3.model.CannedAccessControlList;
////import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.webproject.chatservice.models.User;
//import com.webproject.chatservice.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File; // 나중에 안되면 File 임포트 다른걸로 다시 해보기
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class S3Uploader {
//
//    // 1. MultipleFile 전달 받는다
//    // 2. S3 에 전달할 수 있도록 MultiPartFile 을 File 로 전환
//    // 3. 전환된 File 을 S3 에 Public 읽기 권한으로 put
//    // 4. 로컬에 생성된 File 삭제
//    // 5. 업로드된 파일의 S3 URL 주소를 반환
//
//    private final AmazonS3Client amazonS3Client;
//    private final UserRepository userRepository;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//
//        return upload(uploadFile, dirName);
//    }
//
//    private String upload(File uploadFile, String dirName) {
//        String fileName = dirName + "/" + uploadFile.getName();
//        String uploadImageUrl = putS3(uploadFile, fileName);
//        removeNewFile(uploadFile);
////        이미지 url 저장 로직
////        User user = new User();
////        user.setProfileUrl(uploadImageUrl);
////        userRepository.save(user);
//        return uploadImageUrl;
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        } else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File(file.getOriginalFilename());
//        if(convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//
//        return Optional.empty();
//    }
//}
//

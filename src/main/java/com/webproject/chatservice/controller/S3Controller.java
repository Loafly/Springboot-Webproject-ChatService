package com.webproject.chatservice.controller;

import com.webproject.chatservice.models.User;
import com.webproject.chatservice.utils.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PostRemove;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final Uploader uploader;

    @PostMapping("/api/s3upload")
    public String imgUpload(@RequestParam("data") MultipartFile file) throws IOException {
        String profileUrl = uploader.upload(file, "static");
        return profileUrl;
    }
}

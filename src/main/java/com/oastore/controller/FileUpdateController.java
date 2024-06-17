package com.oastore.controller;

import com.oastore.pojo.Result;
import com.oastore.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUpdateController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile multipartFile) throws Exception {
        String originFilename = multipartFile.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+originFilename.substring(originFilename.lastIndexOf("."));
        //multipartFile.transferTo(new File("C:\\Users\\C\\Pictures\\Test\\"+filename));
        String url= AliOssUtil.uploadImage(filename, multipartFile.getInputStream());
        return Result.success(url);
    }
}

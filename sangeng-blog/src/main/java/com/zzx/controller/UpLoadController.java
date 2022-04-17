package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.UpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 19:02
 **/
@RestController
public class UpLoadController {

    @Autowired
    UpLoadService upLoadService;

    @PostMapping("/upload")
//    @SystemLog(businessName = "上传文件")
    public ResponseResult upLoadImg(MultipartFile img) {
        return upLoadService.upLoadOss(img);
    }
}

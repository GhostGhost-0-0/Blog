package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.UpLoadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: SGBlog
 * @Description 前台上传文件接口
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 19:02
 **/
@RestController
@Api(description = "上传文件相关接口")
public class UpLoadController {

    @Autowired
    UpLoadService upLoadService;

    @PostMapping("/upload")
//    @SystemLog(businessName = "上传文件")
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @ApiImplicitParam(name = "img", value = "以 MultipartFile 对象接受的图片")
    public ResponseResult upLoadImg(MultipartFile img) {
        return upLoadService.upLoadOss(img);
    }
}

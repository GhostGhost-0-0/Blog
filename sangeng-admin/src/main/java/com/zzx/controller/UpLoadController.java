package com.zzx.controller;

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
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-01 13:27
 * @Description: 文件上传接口
 * @Version: 1.0
 */
@RestController
@Api(description = "文件上传接口")
public class UpLoadController {

    @Autowired
    private UpLoadService upLoadService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @ApiImplicitParam(name = "img", value = "以 MultipartFile 对象接受的图片")
    public ResponseResult upLoadImg(MultipartFile img) {
        return upLoadService.upLoadOss(img);
    }
}

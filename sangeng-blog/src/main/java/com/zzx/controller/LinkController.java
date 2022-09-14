package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SGBlog
 * @Description 前台友链接口
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 14:27
 **/
@RestController
@RequestMapping("/link")
@Api(description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "获取全部友链")
    @ApiOperation(value = "友链", notes = "获取所有友链")
    public ResponseResult getAllLink() {
        return linkService.getLinkList();
    }
}

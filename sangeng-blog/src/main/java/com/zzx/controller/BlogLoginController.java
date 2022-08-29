package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.LoginUserDto;
import com.zzx.domain.entity.User;
import com.zzx.domain.service.BlogLoginService;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:02
 **/
@RestController
@Api(description = "登录相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "前台登录")
    @ApiOperation(value = "登录", notes = "用户 token 判断登录")
    public ResponseResult login(@RequestBody LoginUserDto loginUserDto) {
        User user = BeanCopyUtils.copyBean(loginUserDto, User.class);
        if (!StringUtils.hasText(user.getUserName())) {
            //提示必须填写用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "退出登录")
    @ApiOperation(value = "登出", notes = "用户退出")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}

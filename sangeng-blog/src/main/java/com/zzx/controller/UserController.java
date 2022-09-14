package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.RegisterUserDto;
import com.zzx.domain.dto.UpdateUserDto;
import com.zzx.domain.entity.User;
import com.zzx.domain.service.UserService;
import com.zzx.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SGBlog
 * @Description 前台用户接口
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 15:03
 **/
@RestController
@RequestMapping("/user")
@Api(description = "用户信息相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    @ApiOperation(value = "用户信息", notes = "获取用户的个人信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "修改用户个人信息")
    @ApiOperation(value = "修改用户信息", notes = "修改用户的个人信息，昵称，头像啥的")
    public ResponseResult updateUserInfo(@RequestBody UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    @ApiOperation(value = "注册", notes = "注册一个新用户")
    public ResponseResult register (@RequestBody RegisterUserDto registerUserDto) {
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);
        return userService.register(user);
    }
}

package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddUserDto;
import com.zzx.domain.dto.ChangeUserStatusDto;
import com.zzx.domain.dto.UpdateUserDto;
import com.zzx.domain.dto.UserListDto;
import com.zzx.domain.group.Save;
import com.zzx.domain.group.Update;
import com.zzx.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-05 13:27
 * @Description: 后台用户接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system/user")
@Api(description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询用户列表")
    @ApiOperation(value = "查询用户", notes = "分页查询用户列表，可根据用户名、手机号、用户状态查询")
    public ResponseResult selectUserListPage(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        return userService.selectUserListPage(pageNum, pageSize, userListDto);
    }

    @PostMapping("")
    @SystemLog(businessName = "添加用户")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public ResponseResult addAdminUser(@RequestBody @Validated({Save.class}) AddUserDto userDto) {
        return userService.addAdminUser(userDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取用户详情")
    @ApiOperation(value = "获取用户详情", notes = "根据用户 id 获取用户详情")
    public ResponseResult getAdminUserDetail(@PathVariable("id") Long userId) {
        return userService.getAdminUserDetail(userId);
    }

    @PutMapping()
    @SystemLog(businessName = "修改用户信息")
    @ApiOperation(value = "修改用户", notes = "修改用户信息")
    public ResponseResult updateAdminUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateAdminUser(updateUserDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除用户")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public ResponseResult deleteAdminUser(@PathVariable("id") Long userId) {
        return userService.deleteAdminUser(userId);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "更改用户状态")
    @ApiOperation(value = "更改用户状态", notes = "更改用户状态")
    public ResponseResult changeUserStatus(@RequestBody @Validated({Update.class}) ChangeUserStatusDto changeUserStatusDto) {
        return userService.changeUserStatus(changeUserStatusDto);
    }
}

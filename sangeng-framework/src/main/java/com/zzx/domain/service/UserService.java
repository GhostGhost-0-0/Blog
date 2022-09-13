package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddUserDto;
import com.zzx.domain.dto.ChangeUserStatusDto;
import com.zzx.domain.dto.UpdateUserDto;
import com.zzx.domain.dto.UserListDto;
import com.zzx.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 15:06:20
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserListPage(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addAdminUser(AddUserDto userDto);

    ResponseResult getAdminUserDetail(Long userId);

    ResponseResult updateAdminUser(UpdateUserDto updateUserDto);

    ResponseResult deleteAdminUser(Long userId);

    ResponseResult changeUserStatus(ChangeUserStatusDto changeUserStatusDto);
}


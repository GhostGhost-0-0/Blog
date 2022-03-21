package com.zzx.domain.service;

import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.User;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:04
 **/
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}

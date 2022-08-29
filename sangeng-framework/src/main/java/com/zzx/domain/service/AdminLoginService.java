package com.zzx.domain.service;

import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.User;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.service
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-23 15:20
 * @Description: 后台登录业务
 * @Version: 1.0
 */
public interface AdminLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}

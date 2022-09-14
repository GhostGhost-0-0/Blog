package com.zzx.utils;

import com.zzx.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: SGBlog
 * @Description Security 工具类
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 16:45
 **/
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
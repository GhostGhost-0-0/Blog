package com.zzx.domain.service.impl;

import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.LoginUser;
import com.zzx.domain.entity.User;
import com.zzx.domain.service.AdminLoginService;
import com.zzx.utils.JwtUtil;
import com.zzx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.service.impl
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-23 15:27
 * @Description: 后台登录实现类
 * @Version: 1.0
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取 userId 生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入 Redis
        redisCache.setCacheObject("blogAdminLogin:"+userId, loginUser);

        //把 token 和 userInfo 封装 返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取 token，解析获取 userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取 userId
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // 从 redis 中删除 blogAdminLogin 信息
        redisCache.deleteObject("blogAdminLogin"+userId);
        return ResponseResult.okResult();
    }
}

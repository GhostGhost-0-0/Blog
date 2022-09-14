package com.zzx.domain.service.impl;

import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.LoginUser;
import com.zzx.domain.entity.User;
import com.zzx.domain.service.BlogLoginService;
import com.zzx.domain.vo.BlogLoginVo;
import com.zzx.domain.vo.UserInfoVo;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.JwtUtil;
import com.zzx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: SGBlog
 * @Description 前台登录实现类
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:05
 **/
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("blogLogin:"+userId, loginUser);

        //把 token 和 userInfo 封装 返回
        //把 User 转换成 UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogLoginVo vo = new BlogLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取 token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //解析token 获取userId
        Long userId = loginUser.getUser().getId();
        //从redis删除用户信息
        redisCache.deleteObject("blogLogin:" + userId);
        return ResponseResult.okResult();
    }
}

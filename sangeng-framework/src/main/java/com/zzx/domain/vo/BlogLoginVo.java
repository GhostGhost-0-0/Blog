package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: SGBlog
 * @Description 前台登录 vo
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginVo {

    private String token;
    private UserInfoVo userInfoVo;
}

package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 17:19
 **/
@Data
@Accessors(chain = true)
public class UserInfoVo {

    //主键
    private Long id;
    //呢称
    private String nickName;
    //头像
    private String avatar;

    private String sex;

    private String email;
}

package com.zzx.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo.admin
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-07 14:29
 * @Description: 修改用户 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserVo {

    //用户编号
    private Long id;

    //用户名
    private String userName;

    //用户昵称
    private String nickName;

    //用户密码
    private String password;

    //用户手机号
    private String phonenumber;

    //用户邮箱
    private String email;

    //用户性别（0男，1女，2未知）
    private String sex;

    //账号状态（0正常 1停用）
    private String status;

    //角色 id
    private List<Long> roleIds;

    //角色名
    private List<String> roles;
}

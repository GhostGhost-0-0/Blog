package com.zzx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-05 13:38
 * @Description: 后台用户 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVo {

    private Long id;

    private String userName;

    private String nickName;

    private String status;

    private String phonenumber;

    private Date createTime;
}

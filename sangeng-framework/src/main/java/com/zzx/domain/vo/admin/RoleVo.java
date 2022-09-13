package com.zzx.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo.admin
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-06 14:47
 * @Description: 角色 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {

    //角色编号
    private Long id;

    //角色名称
    private String roleName;

    //角色权限字符串
    private String roleKey;

    //角色显示顺序
    private String roleSort;

    //角色状态（0正常，1停用）
    private String status;

    //创建时间
    private Date createTime;
}

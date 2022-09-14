package com.zzx.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo.admin
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-14 13:56
 * @Description: 添加角色时的菜单权限列表 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AddRoleMenuListVo {

    //菜单编号
    private Long id;
    //父菜单编号
    private Long parentId;
    //菜单名称
    private String label;
    //子菜单
    private List<AddRoleMenuListVo> children;
}

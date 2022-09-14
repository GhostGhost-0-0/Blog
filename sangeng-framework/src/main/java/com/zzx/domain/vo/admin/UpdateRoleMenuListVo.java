package com.zzx.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo.admin
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-14 14:26
 * @Description: 修改角色时的菜单权限列表 vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleMenuListVo {

    private List<AddRoleMenuListVo> menus;
    private List<Long> checkedKeys;
}

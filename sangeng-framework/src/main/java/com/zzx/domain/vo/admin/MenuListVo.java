package com.zzx.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.vo.admin
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-13 14:38
 * @Description: 菜单列表vo
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVo {

    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    //创建时间
    private Date createTime;
    //备注
    private String remark;
    //
    private Integer isFrame;
}

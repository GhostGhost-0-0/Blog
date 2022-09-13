package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-13 14:59
 * @Description: 新增菜单 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "新增菜单 dto")
public class AddMenuDto {

    @ApiModelProperty(value = "菜单编号")
    private Long id;

    //菜单名称
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    //父菜单ID
    @ApiModelProperty(value = "父菜单编号")
    private Long parentId;

    //显示顺序
    @NotNull(message = "菜单的显示顺序不能为空")
    @ApiModelProperty(value = "菜单显示顺序")
    private Integer orderNum;

    //路由地址
    @NotBlank(message = "菜单的路由地址不能为空")
    @ApiModelProperty(value = "菜单路由地址")
    private String path;

    //组件路径
    @ApiModelProperty(value = "组件路径")
    private String component;

    //菜单类型（M目录 C菜单 F按钮）
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    //菜单状态（0显示 1隐藏）
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String visible;

    //菜单状态（0正常 1停用）
    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private String status;

    //权限标识
    @ApiModelProperty(value = "权限标识")
    private String perms;

    //菜单图标
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    //是否为外链（0是 1否）
    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Integer isFrame;
}

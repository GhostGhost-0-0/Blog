package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-13 20:23
 * @Description: 添加角色 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加角色 dto")
public class AddRoleDto {

    //角色编号
    @ApiModelProperty(value = "角色编号")
    private Long id;

    //角色名称
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    //角色权限字符串
    @NotBlank(message = "角色权限字符串不能为空")
    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    //显示顺序
    @NotNull(message = "角色顺序不能为空")
    @ApiModelProperty(value = "角色顺序")
    private Integer roleSort;

    //角色状态（0正常 1停用）
    @NotBlank(message = "角色状态不能为空")
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    //角色所选取的菜单编号
    @ApiModelProperty(value = "角色所选取的菜单编号")
    private List<Long> menuIds;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;
}

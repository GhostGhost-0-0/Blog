package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-06 14:36
 * @Description: 角色dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色 dto")
public class RoleListDto {

    @ApiModelProperty(notes = "角色编号")
    private Long id;

    //角色名称
    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    //角色权限字符串
    @ApiModelProperty(notes = "角色权限字符串")
    private String roleKey;

    //显示顺序
    @ApiModelProperty(notes = "显示顺序")
    private Integer roleSort;

    //角色状态（0正常 1停用）
    @ApiModelProperty(notes = "角色状态（0正常，1停用）")
    private String status;

    //创建时间
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
}

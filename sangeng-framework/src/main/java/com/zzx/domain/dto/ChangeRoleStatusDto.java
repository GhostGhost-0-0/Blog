package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-14 16:36
 * @Description: 修改角色状态 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "修改角色状态 dto")
public class ChangeRoleStatusDto {

    @NotNull(message = "角色编号不能为空")
    @ApiModelProperty(value = "角色编号")
    private Long roleId;

    @NotBlank(message = "角色状态不能为空")
    @ApiModelProperty(value = "角色状态")
    private String status;
}

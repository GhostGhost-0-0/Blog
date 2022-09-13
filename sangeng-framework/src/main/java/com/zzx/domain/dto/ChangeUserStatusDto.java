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
 * @CreateTime: 2022-09-07 13:34
 * @Description: 改变用户状态dto
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "改变用户状态 dto")
public class ChangeUserStatusDto {

    @NotNull(message = "用户编号不能为空")
    @ApiModelProperty(notes = "用户编号")
    private Long id;

    @NotBlank(message = "状态码不能为空")
    @ApiModelProperty(notes = "用户状态")
    private String status;
}

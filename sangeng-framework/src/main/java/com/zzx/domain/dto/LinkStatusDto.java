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
 * @CreateTime: 2022-08-31 18:20
 * @Description: 友链审核状态 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "友链审核状态 dto")
public class LinkStatusDto {

    @NotNull
    @ApiModelProperty(notes = "友链 id")
    private Long id;

    @ApiModelProperty(notes = "友链的审核状态")
    @NotBlank(message = "友链的审核状态不能为空")
    private String status;
}

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
 * @CreateTime: 2022-09-13 14:25
 * @Description: 菜单列表 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "菜单列表 dto")
public class MenuListDto {

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单状态")
    private String status;
}

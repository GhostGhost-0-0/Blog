package com.zzx.domain.dto;

import com.zzx.domain.group.Save;
import com.zzx.domain.group.Update;
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
 * @CreateTime: 2022-08-31 19:24
 * @Description: 分类列表 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分类列表 dto")
public class CategoryListDto {

    @ApiModelProperty(notes = "分类 id")
    @NotNull(groups = Update.class)
    private Long id;

    //分类名
    @ApiModelProperty(notes = "分类名称")
    @NotBlank(message = "分类名不能为空", groups = {Save.class, Update.class})
    private String name;

    //父分类id，如果没有父分类为-1
    @ApiModelProperty(notes = "分类的父 id，如果没有父分类为 -1")
    @NotNull
    private Long pid;

    //描述
    @ApiModelProperty(notes = "分类的描述")
    private String description;

    //状态0:正常,1禁用
    @ApiModelProperty(notes = "分类的状态，0-正常，1-停用")
    private String status;
}

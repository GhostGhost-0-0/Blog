package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-29 20:32
 * @Description: 标签 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "标签传输用的 dto")
public class TagListDto {

    //主键 id
    @ApiModelProperty(notes = "标签的主键 id")
    private Long id;

    //标签名
    @ApiModelProperty(notes = "标签名")
    private String name;

    //备注
    @ApiModelProperty(notes = "标签的备注")
    private String remark;
}

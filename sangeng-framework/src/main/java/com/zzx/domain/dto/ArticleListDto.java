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
 * @CreateTime: 2022-09-01 11:36
 * @Description: 博文 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "博文 dto")
public class ArticleListDto {

    @ApiModelProperty(notes = "博文标题")
    private String title;

    @ApiModelProperty(notes = "博文摘要")
    private String summary;
}

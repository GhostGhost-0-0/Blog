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
 * @CreateTime: 2022-09-01 12:26
 * @Description: 增加博文 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "增加博文 dto")
public class AddArticleDto {

    @ApiModelProperty(value = "博文 id")
    private Long id;

    @ApiModelProperty(value = "博文标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "博文摘要")
    private String summary;

    @ApiModelProperty(value = "博文缩略图")
    @NotBlank(message = "缩略图不能为空")
    private String thumbnail;

    @ApiModelProperty(value = "博文正文内容")
    @NotBlank(message = "正文不能为空")
    private String content;

    @ApiModelProperty(value = "博文分类 id，只能有一个分类")
    @NotNull
    private Long categoryId;

    @ApiModelProperty(value = "浏览量")
    private Long viewCount;

    @ApiModelProperty(value = "博文标签 id，可以有多个标签")
    private List<Long> tags;

    @ApiModelProperty(value = "是否允许评论，0-否，1-是")
    private String isComment;

    @ApiModelProperty(value = "是否置顶，0-否，1-是")
    private String isTop;

    @ApiModelProperty(value = "博文状态，0-发布，1-草稿箱")
    private String status;
}

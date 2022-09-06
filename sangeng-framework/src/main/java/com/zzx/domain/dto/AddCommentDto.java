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
 * @CreateTime: 2022-08-22 15:44
 * @Description: 添加评论用的 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论用的 dto")
public class AddCommentDto {

    //主键
    @ApiModelProperty(notes = "主键")
    private Long id;
    //评论类型（0代表文章评论，1代表友链评论）
    @ApiModelProperty(notes = "评论类型（0 代表文章评论，1 代表友链评论）")
    private String type;
    //文章id
    @ApiModelProperty(notes = "文章 id")
    private Long articleId;
    //根评论id
    @ApiModelProperty(notes = "根评论 id")
    private Long rootId;
    //评论内容
    @ApiModelProperty(notes = "评论内容")
    private String content;
    //所回复的目标评论的userid
    @ApiModelProperty(notes = "所回复的目标评论的 userid")
    private Long toCommentUserId;
    //回复目标评论id
    @ApiModelProperty(notes = "回复目标评论 id")
    private Long toCommentId;
    //创建人的用户id
    @ApiModelProperty(notes = "创建人的用户 id")
    private Long createBy;
    //创建时间
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    //更新人
    @ApiModelProperty(notes = "更新人")
    private Long updateBy;
    //更新时间
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @ApiModelProperty(notes = "删除标志（0 代表未删除，1 代表已删除）")
    private Integer delFlag;
}

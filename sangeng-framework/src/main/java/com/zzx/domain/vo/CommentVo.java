package com.zzx.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-25 14:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;
    //文章 id
    private Long articleId;
    //根评论 id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的 userid
    private Long toCommentUserId;
    //所回复的目标评论的 userName
    private String toCommentUserName;
    //回复目标评论 id
    private Long toCommentId;
    //根评论 userId
    private Long createBy;

    private Date createTime;
    //根评论username
    private String username;
    //子评论
    private List<CommentVo> children;
}

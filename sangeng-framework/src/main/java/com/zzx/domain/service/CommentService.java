package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author 那个小楠瓜
 * @since 2022-02-25 14:16:09
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}


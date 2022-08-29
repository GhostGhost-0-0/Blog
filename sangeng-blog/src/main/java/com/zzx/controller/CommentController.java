package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddCommentDto;
import com.zzx.domain.entity.Comment;
import com.zzx.domain.service.CommentService;
import com.zzx.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-25 14:50
 **/
@RestController
@RequestMapping("/comment")
@Api(description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "查询评论列表")
    @ApiOperation(value = "评论区列表", notes = "获取一页评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章的 id"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "发表评论")
    @ApiOperation(value = "添加评论", notes = "用户发表评论")
    @ApiImplicitParam(name = "comment", value = "一条评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "查询友链评论列表")
    @ApiOperation(value = "友链评论列表", notes = "获取一页友链评论区的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}

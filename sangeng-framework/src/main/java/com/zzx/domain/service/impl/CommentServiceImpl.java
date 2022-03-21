package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.Comment;
import com.zzx.domain.entity.User;
import com.zzx.domain.mapper.CommentMapper;
import com.zzx.domain.mapper.UserMapper;
import com.zzx.domain.service.CommentService;
import com.zzx.domain.service.UserService;
import com.zzx.domain.vo.CommentVo;
import com.zzx.domain.vo.PageVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-02-25 14:16:09
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论 id
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对 articleId 进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //根评论 rootId 为 -1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        //封装vo
        List<CommentVo> commentVoList = toCommentVo(page.getRecords());
        //查询所有根评论对应的子评论集合
        for (CommentVo commentVo : commentVoList) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        //用 stream() 流，函数式编程方式
        /*commentVoList.stream()
                .map(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())))
                .collect(Collectors.toList());*/
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 封装成vo，并设置根评论用户昵称和子评论用户昵称属性
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVo(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        /*Set<Long> ids = new HashSet<>();
        for (Long id : ids) {
            System.out.println(id);
        }
        commentVos.stream()
                .map(commentVo -> ids.add(commentVo.getCreateBy()))
                .collect(Collectors.toSet());
        Map<Long, User> userMap = new HashMap<>();
        //根据 id 集合查询出用户列表
        List<User> userList = userMapper.selectBatchIds(ids);
        //将对应的 id 与 user 对象存放到 Map 中
        userList.stream()
                .map(user -> userMap.put(user.getId(), user))
                .collect(Collectors.toList());
        //遍历 vo 集合
        commentVos.stream()
                .map(commentVo -> {
                    //根据 Map 查询出对应的信息，设置评论人的昵称
                    commentVo.setUsername(userMap.get(commentVo.getCreateBy()).getNickName());
                    //判断 toCommentUserId 是否为 -1
                    if (commentVo.getToCommentUserId() != -1) {
                        commentVo.setToCommentUserName(userMap.get(commentVo.getCreateBy()).getNickName());
                    }
                    return commentVo;
                }).collect(Collectors.toList());*/
        for (CommentVo commentVo : commentVos) {
            //根据 createBy 查询用户昵称
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //判断 toCommentUserId 是否为 -1
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

    /**
     * 根据根评论id查询子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        //根据根评论的id查询子评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        //根据创建时间排序
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        //集合封装转换成vo，因为用BeanCopyUtils直接封装会少两个属性所以用定义好的方法
        List<CommentVo> commentVos = toCommentVo(commentList);
        return commentVos;
    }
}


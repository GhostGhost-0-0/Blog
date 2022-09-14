package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SGBlog
 * @Description 前台博文接口
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 14:07
 **/
@RestController
@RequestMapping("/article")
@Api(description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "查询热门文章列表")
    @ApiOperation(value = "热门文章列表", notes = "获取一页热门的文章")
    public ResponseResult hostArticleList() {
        //查询热门文章，封装成 ResponseResult 返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "查询文章列表")
    @ApiOperation(value = "所有文章列表", notes = "获取所有的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "categoryId", value = "分类标签的 id")
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询文章详情")
    @ApiOperation(value = "文章详情", notes = "获取文章的详细信息")
    @ApiImplicitParam(name = "id", value = "文章的 id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量到redis中")
    @ApiOperation(value = "浏览量", notes = "更新文章的浏览量信息")
    @ApiImplicitParam(name = "id", value = "文章的 id")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }
}

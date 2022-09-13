package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddArticleDto;
import com.zzx.domain.dto.ArticleListDto;
import com.zzx.domain.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-01 11:30
 * @Description: 博文接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取博文分页列表")
    @ApiOperation(value = "博文分页列表", notes = "获取博文的分页列表")
    public ResponseResult selectArticleListPage(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        return articleService.selectArticleListPage(pageNum, pageSize, articleListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "博文信息")
    @ApiOperation(value = "获取博文信息", notes = "获取文章信息用于修改展示")
    public ResponseResult getAdminArticleDetail(@PathVariable("id") Long articleId) {
        return articleService.getAdminArticleDetail(articleId);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除博文")
    @ApiOperation(value = "删除博文", notes = "逻辑删除博文")
    public ResponseResult deleteArticle(@PathVariable("id") Long articleId) {
        return articleService.deleteArticleById(articleId);
    }

    @PostMapping("")
    @SystemLog(businessName = "增加博文")
    @ApiOperation(value = "增加博文", notes = "增加博文")
    public ResponseResult addArticle(@RequestBody @Validated AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @PutMapping("")
    @SystemLog(businessName = "更新博文")
    @ApiOperation(value = "更新博文", notes = "更新博文")
    public ResponseResult updateArticle(@RequestBody @Validated AddArticleDto addArticleDto) {
        return articleService.updateArticle(addArticleDto);
    }
}

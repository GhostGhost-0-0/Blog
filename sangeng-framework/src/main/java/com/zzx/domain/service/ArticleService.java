package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddArticleDto;
import com.zzx.domain.dto.ArticleListDto;
import com.zzx.domain.entity.Article;

/**
 * @program: SGBlog
 * @Description 博文业务
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 14:02
 **/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult selectArticleListPage(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult deleteArticleById(Long articleId);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult updateArticle(AddArticleDto addArticleDto);

    ResponseResult getAdminArticleDetail(Long articleId);
}

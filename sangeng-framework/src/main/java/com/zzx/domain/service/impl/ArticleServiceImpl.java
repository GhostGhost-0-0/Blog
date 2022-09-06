package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddArticleDto;
import com.zzx.domain.dto.ArticleListDto;
import com.zzx.domain.entity.Article;
import com.zzx.domain.entity.ArticleTag;
import com.zzx.domain.entity.Category;
import com.zzx.domain.mapper.ArticleMapper;
import com.zzx.domain.service.ArticleService;
import com.zzx.domain.service.ArticleTagService;
import com.zzx.domain.service.CategoryService;
import com.zzx.domain.vo.*;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.RedisCache;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 14:04
 **/
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成 ResponseResult 返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询只10条
        Page<Article> page = new Page(1,10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        //bean 拷贝
        /*List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
        }*/
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有 categoryId 就要求查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对 isTop 进行排序
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        //查询 categoryName
        List<Article> articles = page.getRecords();
        //articleId 去查询 articleName 进行设置
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        /*for (Article article : articles) {
            //用循环方式
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }*/

        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据 id 查询文章
        Article article = getById(id);
        //从 redis 中获取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        //将浏览量设置到 article 中
        article.setViewCount(viewCount.longValue());
        //转换vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //查询分类 id
        Long categoryId = articleDetailVo.getCategoryId();
        //根据分类 id 查询分类名称
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装结果返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), Long.valueOf(1));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectArticleListPage(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        queryWrapper.eq(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        List<ArticleAdminListVo> articleAdminListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleAdminListVo.class);

        return ResponseResult.okResult(new PageVo(articleAdminListVos, page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult deleteArticleById(Long articleId) {
        removeById(articleId);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        //把博文和标签的映射关系添加到数据库中
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        updateById(article);
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.updateBatchById(articleTags);
        return ResponseResult.okResult();
    }
}

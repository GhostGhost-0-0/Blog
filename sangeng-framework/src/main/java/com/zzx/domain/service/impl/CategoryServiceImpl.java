package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.CategoryListDto;
import com.zzx.domain.entity.Article;
import com.zzx.domain.entity.Category;
import com.zzx.domain.mapper.CategoryMapper;
import com.zzx.domain.service.ArticleService;
import com.zzx.domain.service.CategoryService;
import com.zzx.domain.vo.CategoryAdminVo;
import com.zzx.domain.vo.CategoryVo;
import com.zzx.domain.vo.PageVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:32:59
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类 id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult selectCategoryListPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.hasText(categoryListDto.getName()), Category::getName, categoryListDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getStatus()), Category::getStatus, categoryListDto.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        List<CategoryAdminVo> categoryAdminVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryAdminVo.class);

        return ResponseResult.okResult(new PageVo(categoryAdminVos, page.getTotal()));
    }

    @Override
    public ResponseResult getCategoryDetail(Long categoryId) {
        Category category = getById(categoryId);
        CategoryAdminVo categoryAdminVo = BeanCopyUtils.copyBean(category, CategoryAdminVo.class);
        return ResponseResult.okResult(categoryAdminVo);
    }

    @Override
    @Transactional
    public ResponseResult addCategory(CategoryListDto categoryListDto) {
        Category category = BeanCopyUtils.copyBean(categoryListDto, Category.class);
        if (categoryNameExist(category.getName())) {
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_EXIST);
        }
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateCategory(CategoryListDto categoryListDto) {
        Category category = BeanCopyUtils.copyBean(categoryListDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteCategory(Long categoryId) {
        removeById(categoryId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryNormal() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> categories = list(queryWrapper);
        List<CategoryAdminVo> categoryAdminVos = BeanCopyUtils.copyBeanList(categories, CategoryAdminVo.class);
        return ResponseResult.okResult(categoryAdminVos);
    }

    private boolean categoryNameExist(String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, categoryName);
        return count(queryWrapper) > 0;
    }
}


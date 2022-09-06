package com.zzx.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.CategoryListDto;
import com.zzx.domain.entity.Category;
import com.zzx.domain.entity.excel.CategoryExcel;
import com.zzx.domain.group.Save;
import com.zzx.domain.group.Update;
import com.zzx.domain.service.CategoryService;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-31 19:13
 * @Description: 分类接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取全部分类的分页列表")
    @ApiOperation(value = "获取全部分类列表", notes = "分页的列表")
    public ResponseResult selectCategoryListPage(@NotNull Integer pageNum,
                                                 @NotNull Integer pageSize, CategoryListDto categoryListDto) {
        return categoryService.selectCategoryListPage(pageNum, pageSize, categoryListDto);
    }

    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "获取状态为正常的分类列表")
    @ApiOperation(value = "获取分类列表", notes = "查找分类状态为可用的分类列表")
    public ResponseResult list() {
        return categoryService.getCategoryNormal();
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取分类详情")
    @ApiOperation(value = "获取分类详情", notes = "根据 id 查找分类的详细信息")
    public ResponseResult getCategoryDetail(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryDetail(categoryId);
    }

    @PostMapping("")
    @SystemLog(businessName = "增加一个分类")
    @ApiOperation(value = "增加分类", notes = "增加一个分类")
    public ResponseResult addCategory(@RequestBody @Validated(Save.class) CategoryListDto categoryListDto) {
        return categoryService.addCategory(categoryListDto);
    }

    @PutMapping("")
    @SystemLog(businessName = "修改分类信息")
    @ApiOperation(value = "修改分类", notes = "修改分类的信息")
    public ResponseResult updateCategory(@RequestBody @Validated(Update.class) CategoryListDto categoryListDto) {
        return categoryService.updateCategory(categoryListDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除分类")
    @ApiOperation(value = "删除分类", notes = "根据 id 逻辑删除分类")
    public ResponseResult deleteCategory(@PathVariable("id") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出分类 Excel", notes = "导出所有分类的 Excel 文件")
    public void exportCategory(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取分类的数据
            List<Category> list = categoryService.list();
            List<CategoryExcel> categoryExcels = BeanCopyUtils.copyBeanList(list, CategoryExcel.class);
            //将数据写入 excel 中
            EasyExcel.write(response.getOutputStream(), CategoryExcel.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(categoryExcels);
        } catch (Exception e) {
            //失败时返回JSON
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "导出失败");
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}

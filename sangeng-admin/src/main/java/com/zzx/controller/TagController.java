package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.TagListDto;
import com.zzx.domain.entity.Tag;
import com.zzx.domain.entity.excel.TagExcel;
import com.zzx.domain.service.TagService;
import com.zzx.domain.service.impl.ExportExcelService;
import com.zzx.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-22 17:17
 * @Description: 标签接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/content/tag")
@Api(description = "标签相关接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ExportExcelService exportExcelService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取分页标签列表")
    @ApiOperation(value = "分页标签列表", notes = "获取分页标签列表")
    public ResponseResult selectTagListPag(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.selectTagPage(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    @SystemLog(businessName = "获取标签列表名称")
    @ApiOperation(value = "获取标签列表名称", notes = "获取标签列表名称")
    public ResponseResult list() {
        return tagService.getTagList();
    }

    @PostMapping("")
    @SystemLog(businessName = "添加标签")
    @ApiOperation(value = "添加标签", notes = "添加一个标签，标签不能为空，且不能重复")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        return tagService.addTag(tagListDto);
    }

    @GetMapping("{id}")
    @SystemLog(businessName = "获取标签详细信息")
    @ApiOperation(value = "标签详情", notes = "获取标签的详细信息")
    public ResponseResult getTagDetail(@PathVariable("id") Long tagId) {
        return tagService.selectTagById(tagId);
    }

    @PutMapping("")
    @SystemLog(businessName = "修改标签")
    @ApiOperation(value = "修改标签", notes = "修改一个标签，标签不能为空，且不能重复")
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto) {
        return tagService.updateTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除标签")
    @ApiOperation(value = "删除标签", notes = "逻辑删除")
    public ResponseResult deleteTag(@PathVariable("id") Long tagId) {
        return tagService.deleteTag(tagId);
    }

}

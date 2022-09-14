package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.LinkListDto;
import com.zzx.domain.dto.LinkStatusDto;
import com.zzx.domain.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-30 14:48
 * @Description: 友链接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/content/link")
@Api(description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取友链列表")
    @ApiOperation(value = "友链列表", notes = "获取全部友链")
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        return linkService.getAllLink(pageNum, pageSize, linkListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取友链详情")
    @ApiOperation(value = "友链详情", notes = "获取友链的详细信息")
    public ResponseResult getLinkDetail(@PathVariable("id") Long linkId) {
        return linkService.getLinkDetail(linkId);
    }

    @PostMapping("")
    @SystemLog(businessName = "添加友链")
    @ApiOperation(value = "添加友链", notes = "增加一条友链")
    public ResponseResult addLink(@RequestBody @Validated LinkListDto linkListDto) {
        return linkService.addLink(linkListDto);
    }

    @PutMapping("/changeLinkStatus")
    @SystemLog(businessName = "修改友链的审核状态")
    @ApiOperation(value = "修改审核状态", notes = "修改友链的审核状态")
    public ResponseResult changeLinkStatus(@RequestBody @Validated LinkStatusDto linkStatusDto) {
        return linkService.changeLinkStatus(linkStatusDto);
    }

    @PutMapping("")
    @SystemLog(businessName = "修改友链信息")
    @ApiOperation(value = "修改友链", notes = "修改友链的信息，如名称，地址，描述，logo 等")
    public ResponseResult updateLink(@RequestBody LinkListDto linkListDto) {
        return linkService.updateLink(linkListDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long linkId) {
        return linkService.deleteLink(linkId);
    }
}

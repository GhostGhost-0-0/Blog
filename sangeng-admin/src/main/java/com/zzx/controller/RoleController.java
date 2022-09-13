package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.RoleListDto;
import com.zzx.domain.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-06 14:33
 * @Description: 角色相关接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system/role")
@Api(description = "角色相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @SystemLog(businessName = "查询角色列表")
    @ApiOperation(value = "分页查询角色", notes = "分页查询角色的列表")
    public ResponseResult selectRoleListPage(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        return roleService.selectRoleListPage(pageNum, pageSize, roleListDto);
    }

    @GetMapping("/listAllRole")
    @SystemLog(businessName = "获取全部的角色列表")
    @ApiOperation(value = "获取全部角色列表", notes = "获取全部的角色列表")
    public ResponseResult getAllRoleList() {
        return roleService.getAllRoleList();
    }
}

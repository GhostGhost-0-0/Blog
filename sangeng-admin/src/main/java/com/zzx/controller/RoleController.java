package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddRoleDto;
import com.zzx.domain.dto.ChangeRoleStatusDto;
import com.zzx.domain.dto.RoleListDto;
import com.zzx.domain.group.Save;
import com.zzx.domain.group.Update;
import com.zzx.domain.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    @SystemLog(businessName = "分页条件查询角色列表")
    @ApiOperation(value = "分页条件查询角色列表", notes = "分页条件查询角色的列表")
    public ResponseResult selectRoleListPage(@NotNull @ApiParam("页码") Integer pageNum, @NotNull @ApiParam("页数") Integer pageSize, RoleListDto roleListDto) {
        return roleService.selectRoleListPage(pageNum, pageSize, roleListDto);
    }

    @GetMapping("/listAllRole")
    @SystemLog(businessName = "获取全部的角色列表")
    @ApiOperation(value = "获取全部角色列表", notes = "获取全部的角色列表")
    public ResponseResult getAllRoleList() {
        return roleService.getAllRoleList();
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取角色详情")
    @ApiOperation(value = "获取角色详情", notes = "获取角色详情")
    public ResponseResult getRoleDetail(@PathVariable("id") @NotNull Long roleId) {
        return roleService.getRoleDetail(roleId);
    }

    @PostMapping("")
    @SystemLog(businessName = "添加角色")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public ResponseResult addRole(@RequestBody @Validated({Save.class}) AddRoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除角色")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public ResponseResult deleteRole(@PathVariable("id") @NotNull Long roleId) {
        return roleService.deleteRoleById(roleId);
    }

    @PutMapping("")
    @SystemLog(businessName = "修改角色信息")
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    public ResponseResult updateRole(@RequestBody @Validated({Update.class}) AddRoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改角色状态")
    @ApiOperation(value = "修改角色状态", notes = "修改角色状态")
    public ResponseResult changeRoleStatus(@RequestBody @Validated({Update.class}) ChangeRoleStatusDto changeRoleStatusDto) {
        return roleService.changeRoleStatus(changeRoleStatusDto);
    }
}

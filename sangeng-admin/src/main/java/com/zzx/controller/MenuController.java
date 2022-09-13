package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddMenuDto;
import com.zzx.domain.dto.MenuListDto;
import com.zzx.domain.group.Save;
import com.zzx.domain.group.Update;
import com.zzx.domain.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-13 14:23
 * @Description: 菜单接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system/menu")
@Api(description = "菜单接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @SystemLog(businessName = "查询菜单接口")
    @ApiOperation(value = "条件查询菜单", notes = "查询菜单列表")
    public ResponseResult selectMenuList(MenuListDto menuListDto) {
        return menuService.selectMenuList(menuListDto);
    }

    @PostMapping("")
    @SystemLog(businessName = "新增菜单")
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    public ResponseResult addMenu(@RequestBody @Validated({Save.class}) AddMenuDto addMenuDto) {
        return menuService.addMenu(addMenuDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询菜单详情")
    @ApiOperation(value = "查询菜单详情", notes = "查询菜单详情")
    public ResponseResult getMenuDetail(@PathVariable("id") @NotNull Long menuId) {
        return menuService.getMenuDetail(menuId);
    }

    @PutMapping("")
    @SystemLog(businessName = "修改菜单")
    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    public ResponseResult updateMenu(@RequestBody @Validated({Update.class}) AddMenuDto updateMenuDto) {
        return menuService.updateMenu(updateMenuDto);
    }

    @DeleteMapping("{/menuId}")
    @SystemLog(businessName = "删除菜单")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public ResponseResult deleteMenu(@PathVariable @NotNull Long menuId) {
        return menuService.deleteMenuById(menuId);
    }
}

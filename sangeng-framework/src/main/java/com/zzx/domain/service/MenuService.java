package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddMenuDto;
import com.zzx.domain.dto.MenuListDto;
import com.zzx.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-08-24 14:15:03
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult selectMenuList(MenuListDto menuListDto);

    ResponseResult addMenu(AddMenuDto addMenuDto);

    ResponseResult getMenuDetail(Long menuId);

    ResponseResult updateMenu(AddMenuDto updateMenuDto);

    ResponseResult deleteMenuById(Long menuId);
}

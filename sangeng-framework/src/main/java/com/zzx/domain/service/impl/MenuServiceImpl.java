package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddMenuDto;
import com.zzx.domain.dto.MenuListDto;
import com.zzx.domain.entity.Menu;
import com.zzx.domain.mapper.MenuMapper;
import com.zzx.domain.service.MenuService;
import com.zzx.domain.vo.admin.MenuListVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-08-24 14:15:04
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        if (id == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            // 如果是的话，返回所有的菜单
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 不是的话，根据用户的 id 返回具有的菜单
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }
        // 构建 tree
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    @Override
    public ResponseResult selectMenuList(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(menuListDto.getMenuName()), Menu::getMenuName, menuListDto.getMenuName())
                .eq(StringUtils.hasText(menuListDto.getStatus()), Menu::getStatus, menuListDto.getStatus())
                .orderByAsc(Menu::getOrderNum)
                .orderByAsc(Menu::getParentId);
        List<Menu> menus = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addMenu(AddMenuDto addMenuDto) {
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuDetail(Long menuId) {
        Menu menu = getById(menuId);
        MenuListVo menuVo = BeanCopyUtils.copyBean(menu, MenuListVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateMenu(AddMenuDto updateMenuDto) {
        Menu updateMenu = BeanCopyUtils.copyBean(updateMenuDto, Menu.class);
        //通过id查询菜单
        Menu menu = getById(updateMenu.getId());
        //判断该菜单的上级菜单是否符合要求，可能出现上级菜单是自己的情况，这时候要返回修改失败
        if (updateMenuDto.getParentId().equals(menu.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "修改菜单"+ " \" " +updateMenu.getMenuName() + " \" " +"失败，上级菜单不能选择自己");
        }
        updateById(updateMenu);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteMenuById(Long menuId) {
        removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     * 先找出一级菜单，也就是 parentId 为 0 的菜单，再找出它们的子菜单设置到一级菜单的 children 集合里去
     * @param menus 从数据库里找出来的菜单集合
     * @param parentId 菜单的父 id
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuVoTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuVoTree;
    }

    /**
     * 获取存入参数的子菜单
     * @param menu 通过父 id 筛选出来的菜单
     * @param menus 从数据库里找出来的菜单集合
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> children = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return children;
    }
}


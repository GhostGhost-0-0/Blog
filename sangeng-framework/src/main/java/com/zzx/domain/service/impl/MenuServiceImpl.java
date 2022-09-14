package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddMenuDto;
import com.zzx.domain.dto.MenuListDto;
import com.zzx.domain.entity.Menu;
import com.zzx.domain.entity.RoleMenu;
import com.zzx.domain.mapper.MenuMapper;
import com.zzx.domain.service.MenuService;
import com.zzx.domain.service.RoleMenuService;
import com.zzx.domain.vo.admin.AddRoleMenuListVo;
import com.zzx.domain.vo.admin.MenuListVo;
import com.zzx.domain.vo.admin.UpdateRoleMenuListVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author 那个小楠瓜
 * @since 2022-08-24 14:15:04
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

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
    public ResponseResult treeSelect() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        for (Menu menu : menus) {
            menu.setLabel(menu.getMenuName());
        }
        List<AddRoleMenuListVo> menuVos = BeanCopyUtils.copyBeanList(menus, AddRoleMenuListVo.class);

        List<AddRoleMenuListVo> menuTree = buildRoleMenuTree(menuVos, 0);
        return ResponseResult.okResult(menuTree);
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long roleId) {
        //通过角色 id 查询出该角色所对应的菜单权限
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        //将对应的菜单权限的 id 放入集合中
        List<Long> menuIds = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }
        //获取菜单树
        ResponseResult result = treeSelect();
        List<AddRoleMenuListVo> menuTree = (List<AddRoleMenuListVo>) result.getData();

        return ResponseResult.okResult(new UpdateRoleMenuListVo(menuTree, menuIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteMenuById(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);
        Menu menu = getOne(queryWrapper);
        if (menu != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "存在子菜单不允许删除");
        }
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
     * 先找出一级菜单，也就是 parentId 为 0 的菜单，再找出它们的子菜单设置到一级菜单的 children 集合里去
     * @param menus 转换后的菜单集合
     * @param parentId 菜单的父 id
     * @return
     */
    private List<AddRoleMenuListVo> buildRoleMenuTree(List<AddRoleMenuListVo> menus, long parentId) {
        List<AddRoleMenuListVo> roleMenutree = menus.stream()
                .filter(addRoleMenuListVo -> addRoleMenuListVo.getParentId().equals(parentId))
                .map(addRoleMenuListVo -> addRoleMenuListVo.setChildren(getRoleMenuChildren(addRoleMenuListVo, menus)))
                .collect(Collectors.toList());
        return roleMenutree;
    }

    /**
     * 获取存入参数的子菜单
     * @param addRoleMenuListVo 通过父 id 筛选出来的菜单
     * @param menus 转换后的菜单集合
     * @return
     */
    private List<AddRoleMenuListVo> getRoleMenuChildren(AddRoleMenuListVo addRoleMenuListVo, List<AddRoleMenuListVo> menus) {
        List<AddRoleMenuListVo> children = menus.stream()
                .filter(m -> m.getParentId().equals(addRoleMenuListVo.getId()))
                .map(m -> m.setChildren(getRoleMenuChildren(m, menus)))
                .collect(Collectors.toList());
        return children;
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


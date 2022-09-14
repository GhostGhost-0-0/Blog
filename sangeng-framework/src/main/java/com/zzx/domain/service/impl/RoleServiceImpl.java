package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddRoleDto;
import com.zzx.domain.dto.ChangeRoleStatusDto;
import com.zzx.domain.dto.RoleListDto;
import com.zzx.domain.entity.Role;
import com.zzx.domain.entity.RoleMenu;
import com.zzx.domain.mapper.RoleMapper;
import com.zzx.domain.service.RoleMenuService;
import com.zzx.domain.service.RoleService;
import com.zzx.domain.vo.PageVo;
import com.zzx.domain.vo.admin.RoleVo;
import com.zzx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author 那个小楠瓜
 * @since 2022-08-24 14:17:57
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult selectRoleListPage(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, roleListDto.getRoleName())
                .eq(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, roleListDto.getStatus());
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        return ResponseResult.okResult(new PageVo(roleVos, page.getTotal()));
    }

    @Override
    public ResponseResult getAllRoleList() {
        List<Role> roles = list();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        return ResponseResult.okResult(roleVos);
    }

    @Override
    public ResponseResult getRoleDetail(Long roleId) {
        Role role = getById(roleId);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addRole(AddRoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);
        List<RoleMenu> roleMenus = roleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteRoleById(Long roleId) {
        removeById(roleId);
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateRole(AddRoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, role.getId());

        update(role, updateWrapper);
        //这里角色和菜单的映射表的修改逻辑是先把原先该角色的菜单id都删除再重新加进去
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleDto.getId());
        roleMenuService.remove(queryWrapper);
        List<RoleMenu> roleMenus = roleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(roleDto.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeRoleStatus(ChangeRoleStatusDto changeRoleStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, changeRoleStatusDto.getRoleId())
                .set(Role::getStatus, changeRoleStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }
}


package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddRoleDto;
import com.zzx.domain.dto.ChangeRoleStatusDto;
import com.zzx.domain.dto.RoleListDto;
import com.zzx.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author 那个小楠瓜
 * @since 2022-08-24 14:17:57
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult selectRoleListPage(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult getAllRoleList();

    ResponseResult getRoleDetail(Long roleId);

    ResponseResult addRole(AddRoleDto roleDto);

    ResponseResult deleteRoleById(Long roleId);

    ResponseResult updateRole(AddRoleDto roleDto);

    ResponseResult changeRoleStatus(ChangeRoleStatusDto changeRoleStatusDto);
}

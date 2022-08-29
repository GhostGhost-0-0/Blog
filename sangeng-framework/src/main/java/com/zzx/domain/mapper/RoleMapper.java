package com.zzx.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzx.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-24 14:17:57
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

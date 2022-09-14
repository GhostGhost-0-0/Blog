package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.AddUserDto;
import com.zzx.domain.dto.ChangeUserStatusDto;
import com.zzx.domain.dto.UpdateUserDto;
import com.zzx.domain.dto.UserListDto;
import com.zzx.domain.entity.Role;
import com.zzx.domain.entity.User;
import com.zzx.domain.entity.UserRole;
import com.zzx.domain.mapper.UserMapper;
import com.zzx.domain.service.RoleService;
import com.zzx.domain.service.UserRoleService;
import com.zzx.domain.service.UserService;
import com.zzx.domain.vo.admin.AdminUserVo;
import com.zzx.domain.vo.PageVo;
import com.zzx.domain.vo.UserInfoVo;
import com.zzx.domain.vo.admin.UpdateUserVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author 那个小楠瓜
 * @since 2022-02-24 15:06:20
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户
        User user = getById(userId);
        //封装vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行是否存在判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (phoneNumberExist(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectUserListPage(Integer pageNum, Integer pageSize, UserListDto userListDto) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.hasText(userListDto.getUserName()), User::getUserName, userListDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDto.getPhonenumber()), User::getPhonenumber, userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()), User::getStatus, userListDto.getStatus());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        List<AdminUserVo> adminUserVos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminUserVo.class);

        return ResponseResult.okResult(new PageVo(adminUserVos, page.getTotal()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addAdminUser(AddUserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        save(user);

        List<UserRole> userRoles = userDto.getRoles().stream()
                .map(roleOption -> new UserRole(user.getId(), roleOption))
                .collect(Collectors.toList());
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        userRoleService.saveBatch(userRoles);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAdminUserDetail(Long userId) {
        User user = getById(userId);
        UpdateUserVo updateUserVo = BeanCopyUtils.copyBean(user, UpdateUserVo.class);
        List<Long> roleIds = new ArrayList<>();
        List<String> roleName = new ArrayList<>();
        //从用户角色映射表根据查到的用户 id，查询该用户 id 所具有的角色 id， 一个用户可能不止一个角色
        List<UserRole> roleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        for (UserRole userRole : roleList) {
            roleIds.add(userRole.getRoleId());
        }
        List<Role> roles = roleService.listByIds(roleIds);
        for (Role role : roles) {
            roleName.add(role.getRoleName());
        }
        updateUserVo.setRoleIds(roleIds);
        updateUserVo.setRoles(roleName);
        return ResponseResult.okResult(updateUserVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateAdminUser(UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        updateById(user);
        //查找出该用户的id，将其对应的角色id删除，重新添加修改后的用户角色映射关系
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());

        List<UserRole> userRoles = updateUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());

        userRoleService.remove(queryWrapper);
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteAdminUser(Long userId) {
        removeById(userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeUserStatus(ChangeUserStatusDto changeUserStatusDto) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, changeUserStatusDto.getId()).set(User::getStatus, changeUserStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0;
    }

    private boolean phoneNumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }
}


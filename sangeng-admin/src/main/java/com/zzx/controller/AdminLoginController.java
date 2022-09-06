package com.zzx.controller;

import com.zzx.annotation.SystemLog;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.LoginUserDto;
import com.zzx.domain.entity.LoginUser;
import com.zzx.domain.entity.Menu;
import com.zzx.domain.entity.User;
import com.zzx.domain.service.AdminLoginService;
import com.zzx.domain.service.MenuService;
import com.zzx.domain.service.RoleService;
import com.zzx.domain.vo.AdminUserInfoVo;
import com.zzx.domain.vo.RoutersVo;
import com.zzx.domain.vo.UserInfoVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import com.zzx.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.controller
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-23 15:19
 * @Description: 后台登录
 * @Version: 1.0
 */
@RestController
@Api(description = "后台登录相关接口")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "后台登录")
    @ApiOperation(value = "登录", notes = "用户 token 判断登录")
    public ResponseResult login(@RequestBody LoginUserDto loginUserDto) {
        User user = BeanCopyUtils.copyBean(loginUserDto, User.class);
        if (!StringUtils.hasText(user.getUserName())) {
            //提示必须填写用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    @SystemLog(businessName = "获取后台用户的个人信息")
    @ApiOperation(value = "获取信息", notes = "获取后台登录的用户的信息")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户 id 查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户 id 查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    @SystemLog(businessName = "获取菜单的路由信息")
    @ApiOperation(value = "获取路由信息", notes = "获取菜单的路由信息，便于前端用于展示")
    public ResponseResult<Menu> getRouters() {
        // 获取当前登录用户的 id
        Long userId = SecurityUtils.getUserId();
        // 查询 menu，结果是 tree 的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "退出登录")
    @ApiOperation(value = "退出登录", notes = "用于后台用户退出登录")
    public ResponseResult logout() {
        return adminLoginService.logout();
    }
}

package com.zzx.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author 那个小楠瓜
 * @since 2022-09-06 20:06:45
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole  {
    //用户ID

    private Long userId;
    //角色ID
    private Long roleId;




}

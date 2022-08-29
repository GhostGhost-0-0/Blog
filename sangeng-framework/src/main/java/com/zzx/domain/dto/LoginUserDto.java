package com.zzx.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-22 15:40
 * @Description: 登录的用户 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "登录用的用户 dto")
public class LoginUserDto {
    //主键
    @ApiModelProperty(notes = "主键")
    private Long id;
    //用户名
    @ApiModelProperty(notes = "用户名")
    private String userName;
    //昵称
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    //密码
    @ApiModelProperty(notes = "密码")
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    @ApiModelProperty(notes = "用户类型：0代表普通用户，1代表管理员")
    private String type;
    //账号状态（0正常 1停用）
    @ApiModelProperty(notes = "账号状态（0正常 1停用）")
    private String status;
    //邮箱
    @ApiModelProperty(notes = "邮箱")
    private String email;
    //手机号
    @ApiModelProperty(notes = "手机号")
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    @ApiModelProperty(notes = "用户性别（0男，1女，2未知）")
    private String sex;
    //头像
    @ApiModelProperty(notes = "头像")
    private String avatar;
    //创建人的用户id
    @ApiModelProperty(notes = "创建人的用户id")
    private Long createBy;
    //创建时间
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    //更新人
    @ApiModelProperty(notes = "更新人")
    private Long updateBy;
    //更新时间
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @ApiModelProperty(notes = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

}

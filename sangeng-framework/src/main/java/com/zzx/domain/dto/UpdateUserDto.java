package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-07 14:26
 * @Description: 添加用户 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "修改用户 dto")
public class UpdateUserDto {

    //用户编号
    @ApiModelProperty(value = "用户编码")
    private Long id;

    //用户名
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名称长度必须介于 2 和 20 之间")
    @ApiModelProperty(value = "用户名称：长度必须介于 2 和 20 之间")
    private String userName;

    //用户昵称
    @NotBlank(message = "用户呢称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    //用户密码
    @NotBlank(message = "用户密码不能为空")
    @Size(min = 5, max = 20, message = "用户密码长度必须介于 5 和 20 之间")
    @ApiModelProperty(value = "用户密码长度必须介于 5 和 20 之间")
    private String password;

    //用户手机号
    @Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "手机号码格式不对，正确格式为 1-9 的 11 位数字")
    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    //用户邮箱
    @Email(message = "请输入正确的邮箱地址")
    @ApiModelProperty(value = "邮箱")
    private String email;

    //用户性别（0男，1女，2未知）
    @ApiModelProperty(value = "性别")
    private String sex;

    //账号状态（0正常 1停用）
    @ApiModelProperty(value = "账号状态（0正常，1停用）")
    private String status;

    @ApiModelProperty(value = "用户角色id")
    private List<Long> roleIds;
}

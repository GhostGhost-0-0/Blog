package com.zzx.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.dto
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-22 15:50
 * @Description: 注册用户用的 dto
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "注册用户 dto")
public class RegisterUserDto {
    //主键
    @ApiModelProperty(notes = "主键")
    private Long id;

    //用户名
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名称长度必须介于 2 和 20 之间")
    @ApiModelProperty(notes = "用户名")
    private String userName;

    //昵称
    @NotBlank(message = "用户呢称不能为空")
    @ApiModelProperty(notes = "昵称")
    private String nickName;

    //密码
    @NotBlank(message = "用户密码不能为空")
    @Pattern(regexp = "^(\\w){6,12}$", message = "密码格式不对，正确格式为 6-12 位英文、数字、下划线")
    @ApiModelProperty(notes = "密码")
    private String password;

    //用户类型：0代表普通用户，1代表管理员
    @ApiModelProperty(notes = "用户类型：0代表普通用户，1代表管理员")
    private String type;

    //账号状态（0正常 1停用）
    @ApiModelProperty(notes = "账号状态（0正常 1停用）")
    private String status;

    //邮箱
    @Email(message = "邮箱格式不对")
    @ApiModelProperty(notes = "邮箱")
    private String email;

    //手机号
    @Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "手机号码格式不对，正确格式为 首个数字为 1 的 0-9 的 11 位数字")
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

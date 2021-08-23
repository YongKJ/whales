package com.social.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserVo {
    /**
     * 手机号码
     */
    @NotEmpty(message = "必须填写手机号")
    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$",message = "手机号不正确")
    private String phoneNumber;

    /**
     * 密码
     */
    @NotEmpty(message = "必须填写密码")
    @Length(min = 6,max = 18,message = "密码必须是6-18位")
    private String password;

    /**
     * 昵称
     */
    @NotEmpty(message = "必须填写用户名")
    @Length(min = 4,max = 18,message = "用户名必须是4-18位字符")
    private String username;

    /**
     * 头像
     */
    private String userPortrait;

    /**
     * 性别：1：男；2：女
     */
    @NotEmpty(message = "必须填写性别")
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户状态：1：正常使用；2：冻结状态
     */
    private Integer userStatus;

    /**
     * 验证码：数据库中没有此字段
     */
    @NotEmpty(message = "必须填写验证码")
    private String code;
}

package com.social.whales.user.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user")
public class UserEntity {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String username;

    /**
     * 头像
     */
    @Column(name = "user_portrait")
    private String userPortrait;

    /**
     * 性别：1：男；2：女
     */
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
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户状态：1：正常使用；2：冻结状态
     */
    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取手机号码
     *
     * @return phone_number - 手机号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号码
     *
     * @param phoneNumber 手机号码
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     *
     * @return username - 昵称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置昵称
     *
     * @param username 昵称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取头像
     *
     * @return user_portrait - 头像
     */
    public String getUserPortrait() {
        return userPortrait;
    }

    /**
     * 设置头像
     *
     * @param userPortrait 头像
     */
    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    /**
     * 获取性别：1：男；2：女
     *
     * @return sex - 性别：1：男；2：女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别：1：男；2：女
     *
     * @param sex 性别：1：男；2：女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取用户签名
     *
     * @return signature - 用户签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置用户签名
     *
     * @param signature 用户签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取用户状态：1：正常使用；2：冻结状态
     *
     * @return user_status - 用户状态：1：正常使用；2：冻结状态
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置用户状态：1：正常使用；2：冻结状态
     *
     * @param userStatus 用户状态：1：正常使用；2：冻结状态
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
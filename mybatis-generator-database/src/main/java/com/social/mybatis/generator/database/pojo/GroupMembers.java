package com.social.mybatis.generator.database.pojo;

import javax.persistence.*;

@Table(name = "group_members")
public class GroupMembers {
    /**
     * 交流群账号
     */
    @Column(name = "communication_group_id")
    private String communicationGroupId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户身份[1为群主，0为普通用户]
     */
    @Column(name = "user_identity")
    private String userIdentity;

    /**
     * 获取交流群账号
     *
     * @return communication_group_id - 交流群账号
     */
    public String getCommunicationGroupId() {
        return communicationGroupId;
    }

    /**
     * 设置交流群账号
     *
     * @param communicationGroupId 交流群账号
     */
    public void setCommunicationGroupId(String communicationGroupId) {
        this.communicationGroupId = communicationGroupId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户身份[1为群主，0为普通用户]
     *
     * @return user_identity - 用户身份[1为群主，0为普通用户]
     */
    public String getUserIdentity() {
        return userIdentity;
    }

    /**
     * 设置用户身份[1为群主，0为普通用户]
     *
     * @param userIdentity 用户身份[1为群主，0为普通用户]
     */
    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }
}
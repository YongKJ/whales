package com.social.mybatis.generator.database.pojo;

import javax.persistence.*;

@Table(name = "communication_group")
public class CommunicationGroup {
    /**
     * 主键，且为交流群账号
     */
    @Id
    @Column(name = "group_id")
    private String groupId;

    /**
     * 交流群头像
     */
    @Column(name = "group_portrait")
    private String groupPortrait;

    /**
     * 交流群昵称
     */
    @Column(name = "group_nickname")
    private String groupNickname;

    /**
     * 交流群公告
     */
    @Column(name = "group_notice")
    private String groupNotice;

    /**
     * 交流群模块标签/兴趣模块外键
     */
    @Column(name = "group_type_label")
    private String groupTypeLabel;

    /**
     * 交流群自定义标签/自定义标签外键
     */
    @Column(name = "group_custom_tag")
    private String groupCustomTag;

    /**
     * 群主id
     */
    @Column(name = "group_manager_id")
    private String groupManagerId;

    /**
     * 获取主键，且为交流群账号
     *
     * @return group_id - 主键，且为交流群账号
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置主键，且为交流群账号
     *
     * @param groupId 主键，且为交流群账号
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取交流群头像
     *
     * @return group_portrait - 交流群头像
     */
    public String getGroupPortrait() {
        return groupPortrait;
    }

    /**
     * 设置交流群头像
     *
     * @param groupPortrait 交流群头像
     */
    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    /**
     * 获取交流群昵称
     *
     * @return group_nickname - 交流群昵称
     */
    public String getGroupNickname() {
        return groupNickname;
    }

    /**
     * 设置交流群昵称
     *
     * @param groupNickname 交流群昵称
     */
    public void setGroupNickname(String groupNickname) {
        this.groupNickname = groupNickname;
    }

    /**
     * 获取交流群公告
     *
     * @return group_notice - 交流群公告
     */
    public String getGroupNotice() {
        return groupNotice;
    }

    /**
     * 设置交流群公告
     *
     * @param groupNotice 交流群公告
     */
    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    /**
     * 获取交流群模块标签/兴趣模块外键
     *
     * @return group_type_label - 交流群模块标签/兴趣模块外键
     */
    public String getGroupTypeLabel() {
        return groupTypeLabel;
    }

    /**
     * 设置交流群模块标签/兴趣模块外键
     *
     * @param groupTypeLabel 交流群模块标签/兴趣模块外键
     */
    public void setGroupTypeLabel(String groupTypeLabel) {
        this.groupTypeLabel = groupTypeLabel;
    }

    /**
     * 获取交流群自定义标签/自定义标签外键
     *
     * @return group_custom_tag - 交流群自定义标签/自定义标签外键
     */
    public String getGroupCustomTag() {
        return groupCustomTag;
    }

    /**
     * 设置交流群自定义标签/自定义标签外键
     *
     * @param groupCustomTag 交流群自定义标签/自定义标签外键
     */
    public void setGroupCustomTag(String groupCustomTag) {
        this.groupCustomTag = groupCustomTag;
    }

    /**
     * 获取群主id
     *
     * @return group_manager_id - 群主id
     */
    public String getGroupManagerId() {
        return groupManagerId;
    }

    /**
     * 设置群主id
     *
     * @param groupManagerId 群主id
     */
    public void setGroupManagerId(String groupManagerId) {
        this.groupManagerId = groupManagerId;
    }
}
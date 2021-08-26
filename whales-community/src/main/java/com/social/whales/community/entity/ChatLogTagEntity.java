package com.social.whales.community.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "chat_log_tag")
public class ChatLogTagEntity {
    /**
     * 交流群账号id
     */
    @Column(name = "communication_group_id")
    private String communicationGroupId;

    /**
     * 发送该消息的用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 记录发送时间
     */
    @Column(name = "user_information_time")
    private Date userInformationTime;

    /**
     * 信息标记序列号（群里每有人发一个消息就+1）
     */
    @Column(name = "information_sign")
    private Integer informationSign;

    /**
     * 聊天记录
     */
    @Column(name = "user_information")
    private String userInformation;

    /**
     * 获取交流群账号id
     *
     * @return communication_group_id - 交流群账号id
     */
    public String getCommunicationGroupId() {
        return communicationGroupId;
    }

    /**
     * 设置交流群账号id
     *
     * @param communicationGroupId 交流群账号id
     */
    public void setCommunicationGroupId(String communicationGroupId) {
        this.communicationGroupId = communicationGroupId;
    }

    /**
     * 获取发送该消息的用户id
     *
     * @return user_id - 发送该消息的用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置发送该消息的用户id
     *
     * @param userId 发送该消息的用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取记录发送时间
     *
     * @return user_information_time - 记录发送时间
     */
    public Date getUserInformationTime() {
        return userInformationTime;
    }

    /**
     * 设置记录发送时间
     *
     * @param userInformationTime 记录发送时间
     */
    public void setUserInformationTime(Date userInformationTime) {
        this.userInformationTime = userInformationTime;
    }

    /**
     * 获取信息标记序列号（群里每有人发一个消息就+1）
     *
     * @return information_sign - 信息标记序列号（群里每有人发一个消息就+1）
     */
    public Integer getInformationSign() {
        return informationSign;
    }

    /**
     * 设置信息标记序列号（群里每有人发一个消息就+1）
     *
     * @param informationSign 信息标记序列号（群里每有人发一个消息就+1）
     */
    public void setInformationSign(Integer informationSign) {
        this.informationSign = informationSign;
    }

    /**
     * 获取聊天记录
     *
     * @return user_information - 聊天记录
     */
    public String getUserInformation() {
        return userInformation;
    }

    /**
     * 设置聊天记录
     *
     * @param userInformation 聊天记录
     */
    public void setUserInformation(String userInformation) {
        this.userInformation = userInformation;
    }
}
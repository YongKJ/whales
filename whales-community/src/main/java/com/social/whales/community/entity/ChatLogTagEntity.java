package com.social.whales.community.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
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
     * 信息标记序列号（群里每有人发一个消息就）
     */
    @Column(name = "information_sign")
    private String informationSign;

    /**
     * 聊天记录
     */
    @Column(name = "user_information")
    private String userInformation;

    /**
     * 聊天记录类型
     */
    @Column(name = "information_type")
    private String informationType;
}
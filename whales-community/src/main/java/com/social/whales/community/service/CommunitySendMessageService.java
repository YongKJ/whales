package com.social.whales.community.service;

import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.entity.Client2ServerMessage;

public interface CommunitySendMessageService {

    void sendMessageToGroup(String groupId,ChatLogTagEntity chatLogTagEntity);

    void sendMessageToGroupTest(String groupId, Client2ServerMessage message);
}

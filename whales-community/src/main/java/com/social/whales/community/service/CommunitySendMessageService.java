package com.social.whales.community.service;

import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.entityTest.Client2ServerMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface CommunitySendMessageService {

    void sendMessageToGroupTest(String groupId, Client2ServerMessage message);

    void statusUser(String groupId, String userId);

    void sendMessageToGroup(String groupId,ChatLogTagEntity chatLogTagEntity) ;

    void sendPhotoToGroup(String groupId,String userId,MultipartFile file)throws ExecutionException, InterruptedException;
}

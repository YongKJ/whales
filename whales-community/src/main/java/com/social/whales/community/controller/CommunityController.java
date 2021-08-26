package com.social.whales.community.controller;

import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.service.CommunitySendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private CommunitySendMessageService sendMessageService;

    @GetMapping("/")
    public String communitySocketIndex() {
        return "helloTestIndex.html";
    }

    //收发送信息：在前端发送按钮绑定“群号”，然后每次发送回自动传输到对应的群中
    @MessageMapping("/chat/{groupId}")
    public void sendMessage(@DestinationVariable("groupId")String groupId,@RequestBody ChatLogTagEntity chatLogTagEntity) {
        try {
            sendMessageService.sendMessageToGroup(groupId,chatLogTagEntity);
        }catch (Exception e){

        }
    }
}

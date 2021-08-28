package com.social.whales.community.controller;

import com.social.grace.result.GraceJSONResult;
import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.service.CommunitySendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

@RestController
/*@RequestMapping("/")*/
public class CommunityController {
    @Autowired
    private CommunitySendMessageService sendMessageService;

    @GetMapping("/community")
    public String communitySocketIndex() {
        return "helloTestIndex.html";
    }

    //群成员进入聊天室更改redis中记录
    @SubscribeMapping("/status/{groupId}/{userId}")
    public GraceJSONResult statusUser(@DestinationVariable("groupId")String groupId,@DestinationVariable("userId") String userId){
        try{
            sendMessageService.statusUser(groupId,userId);
        }catch (Exception e){

        }
       return null;
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

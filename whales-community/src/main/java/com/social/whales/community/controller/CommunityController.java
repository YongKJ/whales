package com.social.whales.community.controller;

import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.exception.MessagesException;
import com.social.whales.community.service.CommunitySendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
/*@RequestMapping("/")*/
public class CommunityController {
    @Autowired
    private CommunitySendMessageService sendMessageService;

    @GetMapping("/community")
    public String communitySocketIndex() {
        return "helloTestIndex.html";
    }

    /**
     * @param groupId
     * @param userId
     * @return
     * @SubscribeMapping("/topic/topic1") 标注的方法，只会处理SUBSCRIBE发送的消息。
     * @MessageMapping("/topic/topic1") 标注的方法，只会处理SEND发送的消息。
     */
    //群成员进入聊天室更改redis中记录
    //@SubscribeMapping("/status/{groupId}/{userId}")
    @MessageMapping("/status/{groupId}/{userId}")
    public GraceJSONResult statusUser(@DestinationVariable("groupId")String groupId,@DestinationVariable("userId") String userId){
        //System.out.println("groupId:"+groupId+"---------userId:"+userId);
        try{
            sendMessageService.statusUser(groupId,userId);
            return GraceJSONResult.ok(ResponseStatusEnum.ENTER_GROUP_SUCCESS);
        }catch (Exception e){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ENTER_GROUP_ERROR);
        }
    }

    //收发送信息：在前端发送按钮绑定“群号”，然后每次发送回自动传输到对应的群中
    @MessageMapping("/chat/{groupId}")
    public GraceJSONResult sendMessage(@DestinationVariable("groupId")String groupId, ChatLogTagEntity chatLogTagEntity) {
        try {
            sendMessageService.sendMessageToGroup(groupId,chatLogTagEntity);
            return GraceJSONResult.ok();
        }catch (MessagesException e){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.MESSAGE_SEND_ERROR);
        }
    }
/*    @SubscribeMapping("/status/{userId}")
    public void SubscribeTest2(@DestinationVariable("userId") String userId){
        System.out.println("userId:"+userId);
    }*/
}

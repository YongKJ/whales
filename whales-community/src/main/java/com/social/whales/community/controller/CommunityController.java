package com.social.whales.community.controller;

import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.exception.MessagesException;
import com.social.whales.community.service.CommunitySendMessageService;
import com.social.whales.community.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Controller
/*@RequestMapping("/")*/
public class CommunityController {
    @Autowired
    private CommunitySendMessageService sendMessageService;

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
    public GraceJSONResult statusUser(@DestinationVariable("groupId") String groupId, @DestinationVariable("userId") String userId) {
        try {
            sendMessageService.statusUser(groupId, userId);
            return GraceJSONResult.ok(ResponseStatusEnum.ENTER_GROUP_SUCCESS);
        } catch (Exception e) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ENTER_GROUP_ERROR);
        }
    }

    //收发送信息：在前端发送按钮绑定“群号”，然后每次发送回自动传输到对应的群中
    @MessageMapping("/chat/{groupId}")
    public GraceJSONResult sendMessage(@DestinationVariable("groupId") String groupId, ChatLogTagEntity chatLogTagEntity) {
        try {
            sendMessageService.sendMessageToGroup(groupId, chatLogTagEntity);
            return GraceJSONResult.ok();
        } catch (MessagesException e) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.MESSAGE_SEND_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/photos/{groupId}/{userId}")
    public GraceJSONResult sendPhoto(@PathVariable String groupId, @PathVariable String userId, MultipartFile file) throws ExecutionException, InterruptedException{
        sendMessageService.sendPhotoToGroup(groupId, userId, file);
        return GraceJSONResult.ok();
    }

/*    @SubscribeMapping("/status/{userId}")
    public void SubscribeTest2(@DestinationVariable("userId") String userId){
        System.out.println("userId:"+userId);
    }*/
}

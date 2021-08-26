package com.social.whales.community.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.social.whales.community.entity.Client2ServerMessage;
import com.social.whales.community.entity.Server2ClientMessage;
import com.social.whales.community.service.CommunitySendMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/community")
public class CommunityTestController {
    @Autowired
    private CommunitySendMessageService socketServerService;

    @GetMapping("/")
    public String communitySocketIndex(){
        return "helloTestIndex.html";
    }

    //服务端接收group/chat/13242366884 发送给 /member/13242366884
    @MessageMapping("/chat/{groupId}")
    //@SendTo("/member/13242366884")
    public void communitySocketServer(@DestinationVariable String groupId,Client2ServerMessage message){
        System.out.println("This is groupId:"+groupId);
        if (!StringUtils.isEmpty(groupId)){
            socketServerService.sendMessageToGroupTest(groupId,message);
        }
        //return new Server2ClientMessage("Hello"+s);
    }
}

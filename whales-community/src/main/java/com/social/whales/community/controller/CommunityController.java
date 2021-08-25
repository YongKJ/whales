package com.social.whales.community.controller;

import com.social.whales.community.service.CommunitySocketServerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private CommunitySocketServerService socketServerService;

    @GetMapping("/Socket")
    public String communitySocketIndex(){
        return "";
    }

    //服务端接收group/chat/13242366884 发送给
    @MessageMapping("/chat/{groupId}")
    public void communitySocketServer(@PathVariable("groupId")String groupId){
        if (!StringUtils.isEmpty(groupId)){
            socketServerService.sendGroupAndDataProcess(groupId);
        }
    }
}

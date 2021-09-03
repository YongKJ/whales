package com.social.whales.community.controller;

import com.social.whales.community.entityTest.Client2ServerMessage;
import com.social.whales.community.service.CommunitySendMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/community")
public class CommunityTestController {
    @Autowired
    private CommunitySendMessageService socketServerService;

    @GetMapping("/community")
    public String communitySocketIndex() {
        return "helloTestIndex.html";
    }

    @GetMapping("/communityTow")
    public String communitySocketTow(){return "TwoTestIndex.html";}
}

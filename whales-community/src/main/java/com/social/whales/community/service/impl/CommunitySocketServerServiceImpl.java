package com.social.whales.community.service.impl;

import com.social.whales.community.service.CommunitySocketServerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommunitySocketServerServiceImpl implements CommunitySocketServerService {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendGroupAndDataProcess(String groupId) {
        //TODO 放入Redis缓存再通过rabbit从存入数据库
        simpMessagingTemplate.convertAndSend("/"+groupId);
    }
}

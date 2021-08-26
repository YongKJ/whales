package com.social.whales.community.service.impl;

import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.entity.Client2ServerMessage;
import com.social.whales.community.service.CommunitySendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CommunitySendMessageServiceServiceImpl implements CommunitySendMessageService {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void sendMessageToGroup(String groupId,ChatLogTagEntity chatLogTagEntity) {
 /*       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());*/
        //存入缓存进行数据
        chatLogTagEntity.setUserInformationTime(new Date());
       //redisTemplate.opsForList().leftPush("",chatLogTagEntity);
        //TODO 放入Redis缓存再通过rabbit从存入数据库
        simpMessagingTemplate.convertAndSend("/member/"+groupId,chatLogTagEntity.getUserInformation());
    }

    @Override
    public void sendMessageToGroupTest(String groupId, Client2ServerMessage message) {
        simpMessagingTemplate.convertAndSend("/member/"+groupId,"Hello," + message.getName() + "!");
    }
}

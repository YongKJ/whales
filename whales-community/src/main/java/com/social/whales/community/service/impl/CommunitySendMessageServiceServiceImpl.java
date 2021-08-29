package com.social.whales.community.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.entity.Client2ServerMessage;
import com.social.whales.community.entity.GroupMembersEntity;
import com.social.whales.community.mapper.ChatLogTagMapper;
import com.social.whales.community.mapper.GroupMembersMapper;
import com.social.whales.community.service.CommunitySendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CommunitySendMessageServiceServiceImpl implements CommunitySendMessageService {

    private final static String REDIS_MESSAGE_USER = "redis_message_user";
    private final static String GROUP_MEMBER = "redis_group_member";

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private GroupMembersMapper groupMembersMapper;

    @Override
    public void sendMessageToGroup(String groupId, ChatLogTagEntity chatLogTagEntity) {
        //获取所有群成员信息
        //TODO 从redis中查询
        List<String> members = redisTemplate.opsForList().range(GROUP_MEMBER + ":" + groupId, 0, -1);
        if (members != null && members.size() != 0) {
            List<GroupMembersEntity> entityList = members.stream().map(member -> {
                GroupMembersEntity entity = JSONObject.parseObject(member, GroupMembersEntity.class);
                return entity;
            }).collect(Collectors.toList());

            //redis键命名方式[键+用户名+群号]:redis_message_user:xxxx:xxxx
            entityList.stream().forEach(entity -> {
                String userId = entity.getUserId();
                //从redis中获取成员目前所在的群组号
                String userInGroup = redisTemplate.opsForValue().get(REDIS_MESSAGE_USER + ":" + userId);
                //如果当前的成员不在群众
                if (!userInGroup.equals(groupId)) {
                    //放入消息序列号
                    Boolean hasKey = redisTemplate.hasKey(REDIS_MESSAGE_USER + ":" + userId + ":" + groupId);
                    if (!hasKey) {
                        redisTemplate.opsForList().leftPush(REDIS_MESSAGE_USER + ":" + userId + ":" + groupId, chatLogTagEntity.getInformationSign());
                    }
                }
            });
        } else {
            Example userExample = new Example(GroupMembersEntity.class);
            Example.Criteria criteria = userExample.createCriteria();
            criteria.andEqualTo("communicationGroupId", groupId);
            List<GroupMembersEntity> entityList = groupMembersMapper.selectByExample(userExample);
            entityList.stream().forEach(entity -> {
                redisTemplate.opsForList().leftPush(GROUP_MEMBER + ":" + groupId, JSONObject.toJSONString(entity));
            });
        }
        simpMessagingTemplate.convertAndSend("/member/" + groupId, chatLogTagEntity.getUserInformation());
    }

    @Override
    public void sendMessageToGroupTest(String groupId, Client2ServerMessage message) {
        simpMessagingTemplate.convertAndSend("/member/" + groupId, "Hello," + message.getName() + "!");
    }

    @Override
    public void statusUser(String groupId, String userId) {
        //健格式[健+用户名]:redis_message_user:xxxxxx
        String redisKey = REDIS_MESSAGE_USER + ":" + userId;
        //System.out.println("redisKey:"+redisKey);
        //插入值
        redisTemplate.opsForValue().set(redisKey, groupId);

    }
}

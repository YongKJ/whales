package com.social.whales.community.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.social.whales.community.entity.ChatLogTagEntity;
import com.social.whales.community.entityTest.Client2ServerMessage;
import com.social.whales.community.entity.GroupMembersEntity;
import com.social.whales.community.mapper.GroupMembersMapper;
import com.social.whales.community.service.CommunitySendMessageService;
import com.social.whales.community.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class CommunitySendMessageServiceServiceImpl implements CommunitySendMessageService {

    private final static String REDIS_MESSAGE_USER = "redis_message_user";
    private final static String GROUP_MEMBER = "redis_group_member";
    private final static String GROUP_MESSAGES = "redis_messages";
    private final static String MINIO_BUCKET = "whales-picture";
    private final static String TXT_TYPE = "I1";
    private final static String IMAGE_TYPE = "I2";

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private GroupMembersMapper groupMembersMapper;

    @Autowired
    MinioUtils minioUtils;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public void sendMessageToGroup(String groupId, ChatLogTagEntity chatLogTagEntity){
        //?????????????????????????????????????????????
        simpMessagingTemplate.convertAndSend("/member/" + groupId, chatLogTagEntity.getUserInformation());
        //??????????????????redis?????????
        redisTemplate.opsForList().leftPush(GROUP_MESSAGES + ":" + groupId, JSONObject.toJSONString(chatLogTagEntity));
        //???????????????????????????
        //TODO ???redis?????????
        List<String> members = redisTemplate.opsForList().range(GROUP_MEMBER + ":" + groupId, 0, -1);
        if (members != null && members.size() != 0) {
            List<GroupMembersEntity> entityList = members.stream().map(member -> {
                GroupMembersEntity entity = JSONObject.parseObject(member, GroupMembersEntity.class);
                return entity;
            }).collect(Collectors.toList());

            //redis???????????????[???+?????????+??????]:redis_message_user:xxxx:xxxx
            entityList.stream().forEach(entity -> {
                String userId = entity.getUserId();
                //???redis???????????????????????????????????????
                String userInGroup = redisTemplate.opsForValue().get(REDIS_MESSAGE_USER + ":" + userId);
                //?????????????????????????????????
                if (!userInGroup.equals(groupId)) {
                    //?????????????????????
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
    }

    @Override
    public void sendPhotoToGroup(String groupId, String userId, MultipartFile file) throws ExecutionException, InterruptedException{
        //???????????????????????????????????????????????????groupxxxxxxx/yyyyMMdd
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");//??????????????????
        String name = file.getOriginalFilename();
        String format = s.format(new Date());
        //???????????????minio???????????????
        String pathObject = "/Group" + groupId + "/" + format + "/" + name;
        minioUtils.putObject(file, MINIO_BUCKET, pathObject);
        //??????????????????url????????????
        //TODO ????????????????????????
        String photosUrl = "http://119.23.57.189:9000/" + MINIO_BUCKET + pathObject;
        CompletableFuture.runAsync(() -> {
            simpMessagingTemplate.convertAndSend("/member/photos/" + groupId, photosUrl);
        }, executor);

        CompletableFuture.runAsync(() -> {
            ChatLogTagEntity entity = new ChatLogTagEntity();
            String num = String.valueOf(redisTemplate.opsForList().size(GROUP_MESSAGES + ":" + groupId));
            entity.setUserId(userId);
            entity.setCommunicationGroupId(groupId);
            entity.setUserInformationTime(new Date());
            entity.setInformationSign(num);
            entity.setInformationType(IMAGE_TYPE);
            entity.setUserInformation(photosUrl);
            redisTemplate.opsForList().leftPush(GROUP_MESSAGES + ":" + groupId, JSONObject.toJSONString(entity));
        }, executor);
    }

    @Override
    public void statusUser(String groupId, String userId) {
        //?????????[???+?????????]:redis_message_user:xxxxxx
        String redisKey = REDIS_MESSAGE_USER + ":" + userId;
        //?????????
        redisTemplate.opsForValue().set(redisKey, groupId);
    }

    @Override
    public void sendMessageToGroupTest(String groupId, Client2ServerMessage message) {
        simpMessagingTemplate.convertAndSend("/member/" + groupId, "Hello," + message.getName() + "!");
    }
}

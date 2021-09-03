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
        //让消息先发送出去，再做下面的事
        simpMessagingTemplate.convertAndSend("/member/" + groupId, chatLogTagEntity.getUserInformation());
        //将消息存储如redis缓存中
        redisTemplate.opsForList().leftPush(GROUP_MESSAGES + ":" + groupId, JSONObject.toJSONString(chatLogTagEntity));
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
    }

    @Override
    public void sendPhotoToGroup(String groupId, String userId, MultipartFile file) throws ExecutionException, InterruptedException{
        //设置组与当日时间：文件夹命名方式：groupxxxxxxx/yyyyMMdd
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String name = file.getOriginalFilename();
        String format = s.format(new Date());
        //设置存储在minio的文件路径
        String pathObject = "/Group" + groupId + "/" + format + "/" + name;
        minioUtils.putObject(file, MINIO_BUCKET, pathObject);
        //获取可访问的url文件路径
        //TODO 将地址更换为域名
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
        //健格式[健+用户名]:redis_message_user:xxxxxx
        String redisKey = REDIS_MESSAGE_USER + ":" + userId;
        //插入值
        redisTemplate.opsForValue().set(redisKey, groupId);
    }

    @Override
    public void sendMessageToGroupTest(String groupId, Client2ServerMessage message) {
        simpMessagingTemplate.convertAndSend("/member/" + groupId, "Hello," + message.getName() + "!");
    }
}

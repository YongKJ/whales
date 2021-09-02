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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
/*@RequestMapping("/")*/
public class CommunityController {
    private final static String MINIO_BUCKET = "whales-picture";

    @Autowired
    private CommunitySendMessageService sendMessageService;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MinioUtils minioUtils;

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

/*    @MessageMapping("/photos/{groupId}")
    public void sendPhoto(@DestinationVariable String groupId, PhotoEntity photoEntity){
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        File file = photoEntity.getFile();
        String format = s.format(new Date());
        String pathObject =  "/"+groupId+"_"+format+ "/"+file.getName();
        minioUtils.putObject(file,MINIO_BUCKET,pathObject);
        String photosUrl = "http://119.23.57.189:9000/"+MINIO_BUCKET+pathObject;
        simpMessagingTemplate.convertAndSend("/member/photos/"+groupId,photosUrl);
    }*/

    @PostMapping("/photos/{groupId}")
    public String sendPhoto(@PathVariable String groupId, MultipartFile file) throws UnsupportedEncodingException {
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String name = file.getOriginalFilename();
        String format = s.format(new Date());
        String pathObject =  "/"+groupId+"_"+format+ "/"+name;
        minioUtils.putObject(file,MINIO_BUCKET,pathObject);
        String photosUrl = "http://119.23.57.189:9000/"+MINIO_BUCKET+pathObject;
        return "redirect:http://119.23.57.189:9000/"+MINIO_BUCKET+"/"+groupId+"_"+format+ "/"+URLEncoder.encode(name,"UTF-8");
    }

/*    @SubscribeMapping("/status/{userId}")
    public void SubscribeTest2(@DestinationVariable("userId") String userId){
        System.out.println("userId:"+userId);
    }*/
}

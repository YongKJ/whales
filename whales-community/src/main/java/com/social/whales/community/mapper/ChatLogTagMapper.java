package com.social.whales.community.mapper;


import com.social.my.mapper.MyMapper;
import com.social.whales.community.entity.ChatLogTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface ChatLogTagMapper extends MyMapper<ChatLogTagEntity> {
}
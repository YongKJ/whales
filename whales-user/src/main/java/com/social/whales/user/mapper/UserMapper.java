package com.social.whales.user.mapper;

import com.social.my.mapper.MyMapper;
import com.social.whales.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<UserEntity> {
}
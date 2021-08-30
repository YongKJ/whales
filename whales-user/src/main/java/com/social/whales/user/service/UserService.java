package com.social.whales.user.service;

import com.social.grace.result.GraceJSONResult;
import com.social.vo.UserVo;
import com.social.whales.user.entity.UserEntity;

public interface UserService {
    void register(UserVo userVo);

    GraceJSONResult selectPhone(String phoneOrId);

    GraceJSONResult selectPhoneOrId(String phoneOrId,String passwordOrCode);
}

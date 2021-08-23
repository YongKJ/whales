package com.social.whales.user.service.Impl;

import com.social.vo.UserVo;
import com.social.whales.user.entity.UserEntity;
import com.social.whales.user.exception.PhoneExsitException;
import com.social.whales.user.exception.UsernameExistException;
import com.social.whales.user.mapper.UserMapper;
import com.social.whales.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public void register(UserVo vo) {
        checkPhoneUnique(vo.getPhoneNumber());
        checkUsernameUnique(vo.getUsername());

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(vo,entity);

        String userId = UUID.randomUUID().toString().replaceAll("-", "");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(vo.getPassword());
        entity.setId(userId);
        entity.setPassword(encodePassword);

        userMapper.insert(entity);
    }

    private void checkUsernameUnique(String username) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("username",username);

        int count = userMapper.selectCountByExample(adminExample);
        if (count>0){
            throw new UsernameExistException();
        }
    }

    private void checkPhoneUnique(String phoneNumber) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("phoneNumber",phoneNumber);

        int count = userMapper.selectCountByExample(adminExample);
        if (count>0){
            throw new PhoneExsitException();
        }
    }
}

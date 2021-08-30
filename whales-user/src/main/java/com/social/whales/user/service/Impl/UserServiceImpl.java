package com.social.whales.user.service.Impl;

import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.vo.UserVo;
import com.social.whales.user.entity.UserEntity;
import com.social.whales.user.exception.PhoneExsitException;
import com.social.whales.user.exception.UsernameExistException;
import com.social.whales.user.mapper.UserMapper;
import com.social.whales.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public void register(UserVo vo) {
        //检查电话号码是否重复
        checkPhoneUnique(vo.getPhoneNumber());
        //checkUsernameUnique(vo.getUsername());

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(vo,entity);

        String userId = setString(9,9);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(vo.getPassword());
        entity.setId(userId);
        entity.setPassword(encodePassword);

        userMapper.insert(entity);
    }

    @Override
    public GraceJSONResult selectPhone(String phoneOrId) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("phoneNumber",phoneOrId);
        UserEntity userEntity = userMapper.selectOneByExample(adminExample);
        if (userEntity!=null){
            return GraceJSONResult.ok(userEntity);
        }else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.PHONE_NOTHAS_ERROR);
        }
    }

    @Override
    public GraceJSONResult selectPhoneOrId(String phoneOrId,String passwordOrCode) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("phoneNumber",phoneOrId).orEqualTo("id",phoneOrId);
        UserEntity userEntity = userMapper.selectOneByExample(adminExample);
        if (userEntity!=null&&userEntity.getPassword().equals(passwordOrCode)){
            return GraceJSONResult.ok(userEntity);
        }else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_PASSWORD_ERROR);
        }
    }

    //检查用户名是否重复
    private void checkUsernameUnique(String username) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("username",username);

        int count = userMapper.selectCountByExample(adminExample);
        if (count>0){
            throw new UsernameExistException();
        }
    }

    //检查号码是否重复
    private void checkPhoneUnique(String phoneNumber) {
        Example adminExample = new Example(UserEntity.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("phoneNumber",phoneNumber);

        int count = userMapper.selectCountByExample(adminExample);
        if (count>0){
            throw new PhoneExsitException();
        }
    }

    //设置账号
    private String setString(int indexNum,int randomNum) {
        Random random = new Random();
        //设置开头
        int Index = random.nextInt(indexNum);
        //设置随机位数在8-9位之间
        double num = random.nextDouble() * 1 + randomNum;
        long randomId = new Double((Math.random() + Index) * Math.pow(10, num)).longValue();
        String userId = String.valueOf(randomId);
        return userId;
    }
}

package com.social.whales.user;

import com.alibaba.fastjson.JSONObject;
import com.social.whales.user.entity.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import sun.util.resources.LocaleData;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class WhalesUserApplicationTests {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void redisTest() {
        //System.out.println(redisTemplate.opsForValue().get("redisKey:redis_message_user:13242366884"));
        //redisTemplate.opsForValue().set("redisKey:redis_message_user:13242366884","18819776464");
/*        UserEntity user = new UserEntity();
        user.setUsername("邓军");
        user.setBirthday(new Date());
        user.setSex(1);
        String key = "listRedis:110:" + 1150;
        //redisTemplate.opsForList().leftPush(key, JSONObject.toJSONString(user));
        redisTemplate.opsForSet().add(key, JSONObject.toJSONString(user));*/
        for (int i = 0;i<10;i++){
            Random random = new Random();
            //设置开头
            int nextInt = random.nextInt(9);
            //设置随机位数在8-9位之间
            double v = random.nextDouble() * 1 + 7;
            long l = new Double((Math.random() + nextInt) * Math.pow(10, v)).longValue();
            System.out.println(l);
        }
    }
}

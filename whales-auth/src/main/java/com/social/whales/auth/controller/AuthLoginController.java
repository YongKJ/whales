package com.social.whales.auth.controller;

import com.social.constant.AuthServerConstant;
import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.whales.auth.component.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class AuthLoginController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private SmsUtils smsUtils;

    //send sms
    @PostMapping("/sendCode")
    public GraceJSONResult sendCode(@RequestParam("phone") String phone){
        //进行处理验证码时效问题
        //把手机存储到reids防止同一部手机频繁获取验证码
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            //当前时间减去存储时间
            if (System.currentTimeMillis() - l < 60000) {
                //60s内不能再发验证码
                return GraceJSONResult.errorCustom(ResponseStatusEnum.MOBILE_ERROR);
            }
        }
        //2、在能够发送的情况下，即reids中为存储验证码时
        //截取0到5位数，同时获取当前时间
        String code = UUID.randomUUID().toString().substring(0, 5) + "_" + System.currentTimeMillis();
        //暂时存入redis,同时防止同一个手机号再次发送
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);

        //因为code前端传来的code是“验证码_电话号码”格式
        smsUtils.sendSmsCode(phone, code);
        return GraceJSONResult.ok();
    }

}

package com.social.whales.auth.controller;

import com.social.constant.AuthServerConstant;
import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.vo.UserVo;
import com.social.whales.auth.component.SmsUtils;
import com.social.whales.auth.feign.UserFeignService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthLoginController {
    private final static String LOGIN_IDANDPWD = "IdAndPwd";
    private final static String LOGIN_PHOANDPWD = "PhoAndPwd";
    private final static String LOGIN_PHOANDCODE = "PhoAndCode";

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private SmsUtils smsUtils;

    //发送短信验证码并放入缓存
    @PostMapping("/sendCode")
    public GraceJSONResult sendCode(@RequestParam("phone") String phone) {
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
        String smsCode = UUID.randomUUID().toString().substring(0, 5);
        String rediscode = smsCode + "_" + System.currentTimeMillis();
        //暂时存入redis,同时防止同一个手机号再次发送
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, rediscode, 100, TimeUnit.MINUTES);

        //因为code前端传来的code是“验证码_电话号码”格式
        smsUtils.sendSmsCode(phone, smsCode);
        return GraceJSONResult.ok();
    }

    //进行注册
    @PostMapping("/authRegister")
    public GraceJSONResult authRegister(@RequestBody UserVo userVo) {
        //验证码
        String code = userVo.getCode();
        String phone = userVo.getPhoneNumber();
        String redisWord = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!Strings.isEmpty(redisWord)) {
            if (code.equals(redisWord.split("_")[0])) {
                //验证成功
                userFeignService.registerUser(userVo);
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
                return GraceJSONResult.ok();
            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_EQUALS_ERROR);
            }
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_TIME_ERROR);
        }
    }

    //进行登录
    @PostMapping("/authLogin/{loginWay}")
    public GraceJSONResult authLogin(@PathVariable("loginWay") String loginWay, @RequestParam("phoneOrId") String phoneOrId, @RequestParam String passwordOrCode) {
        //门户网账户密码登录:(IdAndPwd)
        //门户网手机密码登录:(PhoAndPwd)
        //门户网短信登陆:(PhoAndCode)
        if (loginWay.equals(LOGIN_IDANDPWD)||loginWay.equals(LOGIN_PHOANDPWD)) {
            GraceJSONResult result = userFeignService.loginUser(loginWay, phoneOrId, passwordOrCode);
            return result;
        } else if (loginWay.equals(LOGIN_PHOANDCODE)) {
            String redisWord = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phoneOrId);
            if (passwordOrCode.equals(redisWord.split("_")[0])) {
                GraceJSONResult result = userFeignService.loginUser(loginWay, phoneOrId, passwordOrCode);
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phoneOrId);
                return result;
            }else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_EQUALS_ERROR);
            }
        }else {
            return GraceJSONResult.error();
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}

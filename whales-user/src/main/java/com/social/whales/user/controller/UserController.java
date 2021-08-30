package com.social.whales.user.controller;

import com.social.constant.AuthServerConstant;
import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.vo.UserVo;
import com.social.whales.user.entity.UserEntity;
import com.social.whales.user.exception.PhoneExsitException;
import com.social.whales.user.exception.UsernameExistException;
import com.social.whales.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static String LOGIN_IDANDPWD = "IdAndPwd";
    private final static String LOGIN_PHOANDPWD = "PhoAndPwd";
    private final static String LOGIN_PHOANDCODE = "PhoAndCode";

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public GraceJSONResult registerUser(@RequestBody UserVo userVo){
        try{
            userService.register(userVo);
        }catch (PhoneExsitException e){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.PHONE_EXIST_EXCEPTION);
        }catch (UsernameExistException e){
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_EXIST_EXCEPTION);
        }
        return GraceJSONResult.ok();
    }

    @PostMapping("/loginUser")
    public GraceJSONResult loginUser(@RequestParam("loginWay") String loginWay,@RequestParam("phone") String phoneOrId, @RequestParam(required = false) String passwordOrCode){
        if (loginWay.equals(LOGIN_IDANDPWD)||loginWay.equals(LOGIN_PHOANDPWD)) {
            GraceJSONResult result = userService.selectPhoneOrId(phoneOrId,passwordOrCode);
            return result;
        }else if (loginWay.equals(LOGIN_PHOANDCODE)) {
            GraceJSONResult result = userService.selectPhone(phoneOrId);
            return result;
        }else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_LOGIN_ERROR);
        }
    }
}

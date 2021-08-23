package com.social.whales.user.controller;

import com.social.grace.result.GraceJSONResult;
import com.social.grace.result.ResponseStatusEnum;
import com.social.vo.UserVo;
import com.social.whales.user.exception.PhoneExsitException;
import com.social.whales.user.exception.UsernameExistException;
import com.social.whales.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
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
}

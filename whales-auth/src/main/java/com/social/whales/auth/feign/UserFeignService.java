package com.social.whales.auth.feign;

import com.social.grace.result.GraceJSONResult;
import com.social.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("whales-user")
public interface UserFeignService {
    @PostMapping("/user/registerUser")
    GraceJSONResult registerUser(@RequestBody UserVo userVo);
}

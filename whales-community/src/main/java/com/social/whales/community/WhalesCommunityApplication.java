package com.social.whales.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.social.whales.community.mapper"})
public class WhalesCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhalesCommunityApplication.class, args);
    }

}

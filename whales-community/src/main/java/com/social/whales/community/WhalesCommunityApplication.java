package com.social.whales.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"com.social.whales.community.mapper"})
public class WhalesCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhalesCommunityApplication.class, args);
    }

}

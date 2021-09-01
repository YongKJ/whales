package com.social.component;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实体类
 * 爪哇笔记：https://blog.52itstyle.vip
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "min.io")
public class MinIoComponent {

    private String endpoint;
    private String accessKey;
    private String secretKey;

    @Bean
    public MinioClient minClient(){
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient.Builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        return minioClient;
    }
}
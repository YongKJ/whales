package com.social.whales.community.util;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MinioUtil {
    @Autowired
    private MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;
}

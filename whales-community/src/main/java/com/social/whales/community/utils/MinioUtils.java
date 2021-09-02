package com.social.whales.community.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class MinioUtils {
    @Autowired
    MinioClient minioClient;

    @SneakyThrows
    public void putObject(MultipartFile file, String bucket, String pathObject) {
        InputStream stream = file.getInputStream();
        String type = file.getContentType();
        System.out.println(type);
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucket).object(pathObject).stream(
                        stream, file.getSize(), -1)
                        .contentType(type)
                        .build());
    }
}

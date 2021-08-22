package com.social.whales.auth.component;


import com.social.whales.auth.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Data
@Component
public class SmsUtils {
    private String host;// 【1】请求地址 支持http 和 https 及 WEBSOCKET
    private String path;// 【2】后缀
    private String skin;//  【4】签名编号
    private String sign;//  【4】模板编号【联系客服人员申请】
    private String appcode;// 【3】开通服务后 买家中心-查看AppCode
    //private String param;

    //param验证码号
    public void sendSmsCode(String phone, String param) {
/*        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";

        String appcode = "你自己的AppCode";*/
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + param + ",**minute**:5");
        querys.put("smsSignId", sign);
        querys.put("templateId", skin);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
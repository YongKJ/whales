package com.social.whales.community.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    // 这个方法的作用是添加一个服务端点，来接收客户端的连接。
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/whaleSocial").withSockJS();
    }
    @Override
    // 这个方法的作用是定义消息代理，通俗一点讲就是设置消息连接请求的各种规范信息。
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //服务端给客户端发消息的地址的前缀信息
        //@SenTo/subscribe
        registry.enableSimpleBroker("/member");
        //客户端给服务端发消息的地址的前缀
        //@SubscribeMapping/subscribe只接收此前缀的消息
        //@MessageMapping/send
        registry.setApplicationDestinationPrefixes("/group");
        //registry.enableSimpleBroker("/topic");               //设置服务器广播消息的基础路径
        //egistry.setApplicationDestinationPrefixes("/app");
    }
}

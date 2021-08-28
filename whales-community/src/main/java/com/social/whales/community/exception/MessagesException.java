package com.social.whales.community.exception;

public class MessagesException extends RuntimeException{
    public MessagesException() {
        super("消息发送失败");
    }
}

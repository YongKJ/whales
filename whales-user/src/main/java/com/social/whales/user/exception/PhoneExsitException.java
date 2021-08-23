package com.social.whales.user.exception;

public class PhoneExsitException extends RuntimeException {
    public PhoneExsitException() {
        super("手机已存在");
    }
}

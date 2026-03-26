package com.xhc.spring.testdemos.demos.proxy.staticproxy;

public class SmsServiceImpl implements SmsService {

    @Override
    public String send(String phone, String content) {
        return "send success, phone=" + phone + ", content=" + content;
    }
}

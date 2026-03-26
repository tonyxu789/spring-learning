package com.xhc.spring.testdemos.demos.proxy.staticproxy;

public class SmsServiceStaticProxy implements SmsService {

    private final SmsService target;

    public SmsServiceStaticProxy(SmsService target) {
        this.target = target;
    }

    @Override
    public String send(String phone, String content) {
        String result = target.send(phone, content);
        return "before: validate params -> "
                + "target: " + result
                + " -> after: record send log";
    }
}

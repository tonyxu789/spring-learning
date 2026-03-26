package com.xhc.spring.testdemos.demos.proxy.staticproxy;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy/static")
public class StaticProxyController {

    // Example: /proxy/static/sms?phone=13800000000&content=hello
    @GetMapping("/sms")
    public Map<String, Object> sms(@RequestParam(defaultValue = "13800000000") String phone,
                                   @RequestParam(defaultValue = "hello static proxy") String content) {
        SmsService target = new SmsServiceImpl();
        SmsService proxy = new SmsServiceStaticProxy(target);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("interface", SmsService.class.getSimpleName());
        result.put("targetClass", target.getClass().getSimpleName());
        result.put("proxyClass", proxy.getClass().getSimpleName());
        result.put("result", proxy.send(phone, content));
        result.put("summary", "static proxy needs a concrete proxy class that implements the same interface");
        return result;
    }
}

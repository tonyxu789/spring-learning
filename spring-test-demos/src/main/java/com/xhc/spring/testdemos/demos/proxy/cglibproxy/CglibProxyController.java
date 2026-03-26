package com.xhc.spring.testdemos.demos.proxy.cglibproxy;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy/cglib")
public class CglibProxyController {

    // Example: /proxy/cglib/coupon?userId=u1001
    @GetMapping("/coupon")
    public Map<String, Object> coupon(@RequestParam(defaultValue = "u1001") String userId) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CouponService.class);
        enhancer.setCallback(new CouponMethodInterceptor());

        CouponService proxy = (CouponService) enhancer.create();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("targetClass", CouponService.class.getSimpleName());
        result.put("proxyClass", proxy.getClass().getName());
        result.put("isCglibProxy", proxy.getClass().getName().contains("$$EnhancerBySpringCGLIB$$"));
        result.put("result", proxy.sendCoupon(userId));
        result.put("summary", "cglib creates a subclass at runtime and does not require an interface");
        return result;
    }
}

package com.xhc.spring.testdemos.demos.proxy.jdkproxy;

import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy/jdk")
public class JdkDynamicProxyController {

    // Example: /proxy/jdk/order?orderNo=ORD-1001
    @GetMapping("/order")
    public Map<String, Object> order(@RequestParam(defaultValue = "ORD-1001") String orderNo) {
        OrderService target = new OrderServiceImpl();
        OrderService proxy = (OrderService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class[]{OrderService.class},
                new OrderInvocationHandler(target));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("interface", OrderService.class.getSimpleName());
        result.put("targetClass", target.getClass().getSimpleName());
        result.put("proxyClass", proxy.getClass().getName());
        result.put("isJdkProxy", Proxy.isProxyClass(proxy.getClass()));
        result.put("result", proxy.createOrder(orderNo));
        result.put("summary", "jdk dynamic proxy creates proxy objects at runtime and requires an interface");
        return result;
    }
}

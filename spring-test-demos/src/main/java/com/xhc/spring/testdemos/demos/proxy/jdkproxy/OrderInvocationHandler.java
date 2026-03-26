package com.xhc.spring.testdemos.demos.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OrderInvocationHandler implements InvocationHandler {

    private final Object target;

    public OrderInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        return "before: open transaction -> "
                + "target: " + result
                + " -> after: commit transaction";
    }
}

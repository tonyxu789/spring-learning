package com.xhc.spring.testdemos.demos.proxy.jdkproxy;

public class OrderServiceImpl implements OrderService {

    @Override
    public String createOrder(String orderNo) {
        return "create order success, orderNo=" + orderNo;
    }
}

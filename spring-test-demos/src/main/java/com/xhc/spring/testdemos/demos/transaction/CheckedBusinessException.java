package com.xhc.spring.testdemos.demos.transaction;

public class CheckedBusinessException extends Exception {

    public CheckedBusinessException(String message) {
        super(message);
    }
}

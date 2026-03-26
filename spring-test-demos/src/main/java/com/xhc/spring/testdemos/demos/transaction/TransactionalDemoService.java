package com.xhc.spring.testdemos.demos.transaction;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalDemoService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionalDemoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public String transferSuccess(String fromUser, String toUser, BigDecimal amount) {
        validateAmount(amount);
        decreaseBalance(fromUser, amount);
        increaseBalance(toUser, amount);
        return "transfer success";
    }

    @Transactional
    public String transferWithRuntimeExceptionRollback(String fromUser, String toUser, BigDecimal amount) {
        validateAmount(amount);
        decreaseBalance(fromUser, amount);

        if (amount.compareTo(BigDecimal.valueOf(100)) >= 0) {
            throw new IllegalStateException("simulate runtime exception, transaction will roll back");
        }

        increaseBalance(toUser, amount);
        return "transfer success";
    }

    @Transactional(rollbackFor = CheckedBusinessException.class)
    public String transferWithCheckedExceptionRollback(String fromUser, String toUser, BigDecimal amount)
            throws CheckedBusinessException {
        validateAmount(amount);
        decreaseBalance(fromUser, amount);

        if (amount.compareTo(BigDecimal.valueOf(200)) >= 0) {
            throw new CheckedBusinessException("simulate checked exception, rollbackFor makes it roll back");
        }

        increaseBalance(toUser, amount);
        return "transfer success";
    }

    private void decreaseBalance(String username, BigDecimal amount) {
        int rows = jdbcTemplate.update(
                "update demo_account set balance = balance - ? where username = ?",
                amount, username);
        if (rows != 1) {
            throw new IllegalArgumentException("source account not found: " + username);
        }
    }

    private void increaseBalance(String username, BigDecimal amount) {
        int rows = jdbcTemplate.update(
                "update demo_account set balance = balance + ? where username = ?",
                amount, username);
        if (rows != 1) {
            throw new IllegalArgumentException("target account not found: " + username);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }
}

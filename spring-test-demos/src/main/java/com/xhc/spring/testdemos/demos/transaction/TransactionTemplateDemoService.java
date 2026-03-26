package com.xhc.spring.testdemos.demos.transaction;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TransactionTemplateDemoService {

    private final JdbcTemplate jdbcTemplate;

    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateDemoService(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    public String transferSuccess(String fromUser, String toUser, BigDecimal amount) {
        validateAmount(amount);
        return transactionTemplate.execute(status -> {
            decreaseBalance(fromUser, amount);
            increaseBalance(toUser, amount);
            return "transfer success";
        });
    }

    public String transferWithManualRollback(String fromUser, String toUser, BigDecimal amount) {
        validateAmount(amount);
        return transactionTemplate.execute(status -> {
            int debitRows = jdbcTemplate.update(
                    "update demo_account set balance = balance - ? where username = ?",
                    amount, fromUser);
            int creditRows = jdbcTemplate.update(
                    "update demo_account set balance = balance + ? where username = ?",
                    amount, toUser);

            if (debitRows != 1 || creditRows != 1) {
                status.setRollbackOnly();
                return "rolled back manually because at least one account does not exist";
            }
            return "transfer success";
        });
    }

    public String transferWithExceptionRollback(String fromUser, String toUser, BigDecimal amount) {
        validateAmount(amount);
        return transactionTemplate.execute(status -> {
            decreaseBalance(fromUser, amount);

            if (amount.compareTo(BigDecimal.valueOf(100)) >= 0) {
                throw new IllegalStateException("simulate business exception, transaction will roll back");
            }

            increaseBalance(toUser, amount);
            return "transfer success";
        });
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

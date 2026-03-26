package com.xhc.spring.testdemos.demos.transaction;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tx/template")
public class TransactionTemplateDemoController {

    private final TransactionTemplateDemoService transactionTemplateDemoService;

    public TransactionTemplateDemoController(TransactionTemplateDemoService transactionTemplateDemoService) {
        this.transactionTemplateDemoService = transactionTemplateDemoService;
    }

    // Example: /tx/template/success?from=alice&to=bob&amount=50
    @GetMapping("/success")
    public String success(@RequestParam String from,
                          @RequestParam String to,
                          @RequestParam BigDecimal amount) {
        return transactionTemplateDemoService.transferSuccess(from, to, amount);
    }

    // Example: /tx/template/manual-rollback?from=alice&to=unknown&amount=50
    @GetMapping("/manual-rollback")
    public String manualRollback(@RequestParam String from,
                                 @RequestParam String to,
                                 @RequestParam BigDecimal amount) {
        return transactionTemplateDemoService.transferWithManualRollback(from, to, amount);
    }

    // Example: /tx/template/exception-rollback?from=alice&to=bob&amount=100
    @GetMapping("/exception-rollback")
    public String exceptionRollback(@RequestParam String from,
                                    @RequestParam String to,
                                    @RequestParam BigDecimal amount) {
        try {
            return transactionTemplateDemoService.transferWithExceptionRollback(from, to, amount);
        } catch (Exception ex) {
            return "rolled back by exception: " + ex.getMessage();
        }
    }
}

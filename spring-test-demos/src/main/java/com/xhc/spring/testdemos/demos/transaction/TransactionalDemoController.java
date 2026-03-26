package com.xhc.spring.testdemos.demos.transaction;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tx/annotation")
public class TransactionalDemoController {

    private final TransactionalDemoService transactionalDemoService;

    public TransactionalDemoController(TransactionalDemoService transactionalDemoService) {
        this.transactionalDemoService = transactionalDemoService;
    }

    // Example: /tx/annotation/success?from=alice&to=bob&amount=50
    @GetMapping("/success")
    public String success(@RequestParam String from,
                          @RequestParam String to,
                          @RequestParam BigDecimal amount) {
        return transactionalDemoService.transferSuccess(from, to, amount);
    }

    // Example: /tx/annotation/runtime-rollback?from=alice&to=bob&amount=100
    @GetMapping("/runtime-rollback")
    public String runtimeRollback(@RequestParam String from,
                                  @RequestParam String to,
                                  @RequestParam BigDecimal amount) {
        try {
            return transactionalDemoService.transferWithRuntimeExceptionRollback(from, to, amount);
        } catch (Exception ex) {
            return "rolled back by runtime exception: " + ex.getMessage();
        }
    }

    // Example: /tx/annotation/checked-rollback?from=alice&to=bob&amount=200
    @GetMapping("/checked-rollback")
    public String checkedRollback(@RequestParam String from,
                                  @RequestParam String to,
                                  @RequestParam BigDecimal amount) {
        try {
            return transactionalDemoService.transferWithCheckedExceptionRollback(from, to, amount);
        } catch (Exception ex) {
            return "rolled back by checked exception: " + ex.getMessage();
        }
    }
}

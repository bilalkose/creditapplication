package com.bilalkose.creditapplication.controller;

import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;
import com.bilalkose.creditapplication.service.impl.CreditApplicationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/creditapplication/")
public class CreditApplicationController {
    private final CreditApplicationServiceImpl creditApplicationService;


    public CreditApplicationController(CreditApplicationServiceImpl creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }

    @PostMapping("{customerId}")
    public ResponseEntity<CreditApplicationResultDto> save(@PathVariable Long customerId) {
        return ResponseEntity.ok(creditApplicationService.save(customerId));
    }
}

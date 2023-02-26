package com.bilalkose.creditapplication.controller;

import com.bilalkose.creditapplication.dto.CreditApplicationDto;
import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.dto.request.GetCustomerCreditApplicationRequest;
import com.bilalkose.creditapplication.service.CreditApplicationService;
import com.bilalkose.creditapplication.service.impl.CreditApplicationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/creditapplication")
@CrossOrigin("*")
public class CreditApplicationController {
    private final CreditApplicationService creditApplicationService;


    public CreditApplicationController(CreditApplicationServiceImpl creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }
    @PostMapping
   public ResponseEntity<CreditApplicationResultDto> save(@RequestBody GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest) {
       return ResponseEntity.ok(creditApplicationService.save(getCustomerCreditApplicationRequest));
   }



    @GetMapping
    public ResponseEntity<List<CreditApplicationDto>> findAllByCitizenshipNumberAndBirtDay(@RequestBody @Valid GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest){
         return ResponseEntity.ok(this.creditApplicationService.getAllByCitizenshipNumberAndBirthDay(getCustomerCreditApplicationRequest));
    }

}

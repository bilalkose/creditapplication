package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.dto.CreditApplicationDto;
import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;
import com.bilalkose.creditapplication.dto.request.GetCustomerCreditApplicationRequest;

import java.util.List;

public interface CreditApplicationService {

    CreditApplicationResultDto save(GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest);

    List<CreditApplicationDto> getAllByCitizenshipNumberAndBirthDay(GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest);
}

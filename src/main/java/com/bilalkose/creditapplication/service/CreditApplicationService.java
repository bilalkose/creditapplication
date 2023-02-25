package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;

public interface CreditApplicationService {

    CreditApplicationResultDto save(Long customerId);
}

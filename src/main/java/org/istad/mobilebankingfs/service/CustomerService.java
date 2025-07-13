package org.istad.mobilebankingfs.service;

import org.istad.mobilebankingfs.dto.customer.CreateCustomerRequest;
import org.istad.mobilebankingfs.dto.customer.CustomerResponse;
import org.istad.mobilebankingfs.dto.customer.CustomerUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerResponse createCustomer(CreateCustomerRequest request);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse findByEmail(String email);
    CustomerResponse findByPhoneNumber(String phoneNumber);

    CustomerResponse updateCustomerByEmail(String email, CustomerUpdate update);

    void deleteCustomerByEmail(String email);
    void deleteByUuid(String uuid);
}

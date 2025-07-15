package org.istad.mobilebankingfs.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.domain.Customer;
import org.istad.mobilebankingfs.domain.KYC;
import org.istad.mobilebankingfs.domain.Segment;
import org.istad.mobilebankingfs.dto.customer.CreateCustomerRequest;
import org.istad.mobilebankingfs.dto.customer.CustomerResponse;
import org.istad.mobilebankingfs.dto.customer.CustomerUpdate;
import org.istad.mobilebankingfs.mapper.CustomerMapper;
import org.istad.mobilebankingfs.repository.CustomerRepository;
import org.istad.mobilebankingfs.repository.KYCRepository;
import org.istad.mobilebankingfs.repository.SegmentRepository;
import org.istad.mobilebankingfs.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SegmentRepository segmentRepository;
    private final KYCRepository kycRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        // check if customer already exist
        if(customerRepository.existsByEmailAndPhoneNumber(request.email(), request.phoneNumber())
                || kycRepository.existsByNationalCardId(request.nationalCardId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Customer already exists.");
        }

        // get segment of customer
        Segment segment = segmentRepository.findByName(request.segment().toLowerCase())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Segment name not found.")
        );
        Customer customer = new Customer();
        customer.setFullName(request.fullName());
        customer.setGender(request.gender());
        customer.setEmail(request.email());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setRemark(request.remark());
        KYC kyc = new KYC();
        kyc.setIsVerified(false);
        kyc.setNationalCardId(request.nationalCardId());
        kyc.setIsDeleted(false);

        customer.setKyc(kyc);
        customer.setSegment(segment);


        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse findByEmail(String email) {
        return customerMapper.toCustomerResponse(
                customerRepository.findByEmail(email).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Customer not found.")
                )
        );
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerMapper.toCustomerResponse(
                customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Customer not found.")
                )
        );
    }

    @Override
    public CustomerResponse updateCustomerByEmail(String email, CustomerUpdate update) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found.")
        );
        customerMapper.updateUser(update, customer);
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void deleteCustomerByEmail(String email) {
        if(!customerRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Customer not found.");
        }
        customerRepository.deleteByEmail(email);
    }

    @Override
    @Transactional
    public void deleteByUuid(String uuid) {
        if(!customerRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Customer not found.");
        }
        customerRepository.deleteByUuid(uuid);
    }
}

package org.istad.mobilebankingfs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.customer.CreateCustomerRequest;
import org.istad.mobilebankingfs.dto.customer.CustomerUpdate;
import org.istad.mobilebankingfs.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping
    public ResponseEntity<?> createCustomer (@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        return new ResponseEntity<>(customerService.createCustomer(createCustomerRequest)
                , HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(Map.of(
                "customers", customerService.getAllCustomers()),
                HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return new ResponseEntity<>(customerService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> findByPhoneNumber(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(
                customerService.findByPhoneNumber(phoneNumber),
                HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateCustomerByEmail(@PathVariable String email, @Valid @RequestBody CustomerUpdate update) {
        return new ResponseEntity<>(
                customerService.updateCustomerByEmail(email, update),
                HttpStatus.OK);
    }

    @DeleteMapping("/email/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerByEmail(@PathVariable String email) {
        customerService.deleteCustomerByEmail(email);
    }

    @DeleteMapping("/uuid/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerByUuid(@PathVariable String uuid) {
        customerService.deleteByUuid(uuid);
    }
}

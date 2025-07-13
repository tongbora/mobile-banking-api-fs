package org.istad.mobilebankingfs.dto.account;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import org.istad.mobilebankingfs.domain.AccountType;
import org.istad.mobilebankingfs.domain.Customer;
import org.istad.mobilebankingfs.dto.customer.CustomerResponse;

import java.math.BigDecimal;

public record AccountResponse(
         String actNo,
         String actCurrency,
         BigDecimal balance,
         CustomerResponse customer,
         AccountTypeResponse accountType
) {
}

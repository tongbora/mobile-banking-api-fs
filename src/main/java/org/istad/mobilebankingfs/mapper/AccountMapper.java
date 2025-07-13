package org.istad.mobilebankingfs.mapper;


import org.istad.mobilebankingfs.domain.Account;
import org.istad.mobilebankingfs.domain.AccountType;
import org.istad.mobilebankingfs.dto.account.AccountResponse;

import org.istad.mobilebankingfs.dto.account.AccountTypeResponse;
import org.istad.mobilebankingfs.dto.account.AccountUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface AccountMapper {
    AccountResponse toAccountResponse(Account account);
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccount(Account account, @MappingTarget AccountUpdateRequest request);
}

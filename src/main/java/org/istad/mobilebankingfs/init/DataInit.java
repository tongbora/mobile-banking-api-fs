package org.istad.mobilebankingfs.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.domain.AccountType;
import org.istad.mobilebankingfs.repository.AccountTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void init(){
        if(accountTypeRepository.count() < 1){
            AccountType accountType = new AccountType();
            accountType.setName("current");

            AccountType accountType2 = new AccountType();
            accountType2.setName("saving");

            accountTypeRepository.saveAll(
                    List.of(
                            accountType,
                            accountType2
                    )
            );
        }
    }
}

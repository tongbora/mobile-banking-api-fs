package org.istad.mobilebankingfs.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.domain.AccountType;
import org.istad.mobilebankingfs.domain.Segment;
import org.istad.mobilebankingfs.repository.AccountTypeRepository;
import org.istad.mobilebankingfs.repository.SegmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final AccountTypeRepository accountTypeRepository;
    private final SegmentRepository segmentRepository;
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

        if(segmentRepository.count() < 1){
            Segment segment = new Segment();
            segment.setName("gold");

            Segment segment2 = new Segment();
            segment2.setName("silver");

            Segment segment3 = new Segment();
            segment3.setName("regular");

            segmentRepository.saveAll(
                    List.of(
                            segment,
                            segment2,
                            segment3
                    )
            );
        }
    }
}

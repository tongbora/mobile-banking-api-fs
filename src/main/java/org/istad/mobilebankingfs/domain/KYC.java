package org.istad.mobilebankingfs.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class KYC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // uuid

    @Column(unique = true, length = 15)
    private String nationalCardId;
    private Boolean isVerified;
    private Boolean isDeleted;

    @OneToOne(mappedBy = "kyc")
    private Customer customer;

}

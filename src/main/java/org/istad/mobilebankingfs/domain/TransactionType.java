package org.istad.mobilebankingfs.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="transaction_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionType {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String payment;

    @Column(nullable = false)
    private String transfer;

    @OneToMany(mappedBy = "type")
    private List<Transaction> transaction;
}

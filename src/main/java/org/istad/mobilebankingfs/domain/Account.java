package org.istad.mobilebankingfs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"customer", "accountType"})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    private String actNo;

    @Column(nullable = false , length = 50)
    private String actCurrency;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal overLimit;

    private Boolean isDeleted = false;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private AccountType accountType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactionSenders;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> transactionsReceivers;
}

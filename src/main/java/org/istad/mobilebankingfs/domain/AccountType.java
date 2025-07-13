package org.istad.mobilebankingfs.domain;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Table(name="account_types")
@Data
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}

package org.istad.mobilebankingfs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"accounts", "kyc"})
@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String uuid = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false , length = 15)
    private String gender;

    @Column(unique = true)
    private String email;
    @Column(unique = true)

    private String phoneNumber;
    @Column(columnDefinition = "TEXT")

    private String remark;

    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer")
    private KYC kyc;
}

package com.transactions.accounttransaction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity(name = "Transaction")
@Table(name = "Transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    @Id
    @Column
    @GeneratedValue
    private int transactionId;

    @Column
    private BigDecimal transactionValue;

    @Column
    private String operation;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "accountId", nullable = false)
    private Account originAccount;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "accountId", nullable = false)
    private Account targetAccount;

}

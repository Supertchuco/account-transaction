package com.transactions.accounttransaction.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity(name = "Client")
@Table(name = "Client")
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @Column
    @GeneratedValue
    private int clientId;

    @Column
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts;
}

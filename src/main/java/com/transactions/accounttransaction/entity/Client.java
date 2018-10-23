package com.transactions.accounttransaction.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity(name = "Client")
@Table(name = "Client")
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @Column
    private int id;

    @Column
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts;

    public Client (final int id, final String email){
        this.id = id;
        this.email = email;
    }
}

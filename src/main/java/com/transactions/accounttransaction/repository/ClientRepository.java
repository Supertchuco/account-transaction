package com.transactions.accounttransaction.repository;


import com.transactions.accounttransaction.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findById(final int clientId);

}

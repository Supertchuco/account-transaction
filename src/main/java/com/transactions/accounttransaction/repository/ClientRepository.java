package com.transactions.accounttransaction.repository;


import com.transactions.accounttransaction.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findClientId(final int clientId);

    @Query("SELECT client FROM Client client WHERE client.account.accountId = :accountId ")
    Client findCLientByAccountId(final @Param("accountId") int accountId);
}

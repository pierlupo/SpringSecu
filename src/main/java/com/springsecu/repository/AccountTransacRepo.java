package com.springsecu.repository;

import com.springsecu.model.AccountTransac;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransacRepo extends CrudRepository<AccountTransac, Long> {

    List<AccountTransac> findByCustomerIdOrderByTransactionDtDesc(int customerId);

}



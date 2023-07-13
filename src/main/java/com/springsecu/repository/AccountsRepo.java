package com.springsecu.repository;

import com.springsecu.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepo extends CrudRepository<Accounts, Long> {

    Accounts findByCustomerId(int customerId);

}

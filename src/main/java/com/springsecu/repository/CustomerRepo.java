package com.springsecu.repository;

import com.springsecu.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

    //List<Customer> findByEmail(String email);

    Customer findByEmail(String email);
}

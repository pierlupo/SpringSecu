package com.springsecu.repository;

import com.springsecu.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Cardsrepo  extends CrudRepository<Cards, Long> {

    List<Cards> findByCustomerId(int customerId);
}

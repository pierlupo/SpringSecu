package com.springsecu.controller;

import com.springsecu.model.Cards;
import com.springsecu.repository.Cardsrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {


    @Autowired
    private Cardsrepo cardsRepo;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam int id) {
        List<Cards> cards = cardsRepo.findByCustomerId(id);
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }


}

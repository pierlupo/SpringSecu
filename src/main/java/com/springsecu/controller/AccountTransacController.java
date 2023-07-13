package com.springsecu.controller;

import com.springsecu.model.AccountTransac;
import com.springsecu.repository.AccountTransacRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountTransacController {

    @Autowired
    private AccountTransacRepo accountTransacRepo;

    @GetMapping("/myBalance")
    public List<AccountTransac> getBalanceDetails(@RequestParam int id) {
        List<AccountTransac> accountTransac = accountTransacRepo.
                findByCustomerIdOrderByTransactionDtDesc(id);
        if (accountTransac != null ) {
            return accountTransac;
        }else {
            return null;
        }
    }
}

package com.kata.controller;

import com.kata.model.Account;
import com.kata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public Account get(@PathVariable(value = "id")  String id) {
        return accountService.getAccountStatement(id);

    }

    @PostMapping("/{id}")
    public Account create(@PathVariable(value = "id")  String id) {
        return accountService.create(id);

    }

    @PutMapping("/{id}/deposit/{amount}")
    public void deposit(@PathVariable(value = "id")  String id,@PathVariable(value = "amount")   double amount) {
        accountService.deposit(id, amount);
    }

    @PutMapping("/{id}/withdrawal/{amount}")
    public void withdrawal(@PathVariable(value = "id")  String id,@PathVariable(value = "amount")   double amount) {
        accountService.withdrawal(id, amount);
    }
}

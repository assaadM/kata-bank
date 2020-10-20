package com.kata.service;

import com.kata.model.Account;

public interface AccountService {

    void deposit(String idAccount, double amount);

    void withdrawal(String idAccount, double amount);

    Account getAccountStatement(String idAccount);
    Account create(String idAccount);
}

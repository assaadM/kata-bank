package com.kata.repository;

import com.kata.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountRepositoryMemory implements AccountRepository{
    private Map<String, Account> accountMap = new HashMap<>();

    public Account getAccount(String idAccount) {
        if(!accountMap.containsKey(idAccount))
            throw new RuntimeException("Account not found");
        return accountMap.get(idAccount);
    }

    public Account save(Account account) {
        accountMap.put(account.getId(),account);
        return account;
    }
}

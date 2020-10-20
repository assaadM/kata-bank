package com.kata.service;

import com.kata.model.Account;
import com.kata.model.Operation;
import com.kata.model.OperationType;
import com.kata.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public void deposit(String idAccount, double amount) {
        Account account = this.accountRepository.getAccount(idAccount);
        account.setBalance(account.getBalance()+amount);
        account.getOperations().add(Operation.builder().type(OperationType.DEPOSIT).amount(amount)
                .date(LocalDateTime.now()).balance(account.getBalance()).build());
        this.accountRepository.save(account);
    }

    public void withdrawal(String idAccount, double amount) {
        Account account = this.accountRepository.getAccount(idAccount);
        if(account.getBalance()<amount)
            throw new RuntimeException("Operation not authorized: big amount");
        account.setBalance(account.getBalance()-amount);
        account.getOperations().add(Operation.builder().type(OperationType.WITH_DRAWL).amount(amount)
                .date(LocalDateTime.now()).balance(account.getBalance()).build());
        this.accountRepository.save(account);
    }

    @Override
    public Account getAccountStatement(String idAccount) {
        return this.accountRepository.getAccount(idAccount);
    }

    @Override
    public Account create(String idAccount) {
        return this.accountRepository.save(Account.builder().id(idAccount).build());
    }
}

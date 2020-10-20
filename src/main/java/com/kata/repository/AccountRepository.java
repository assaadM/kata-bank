package com.kata.repository;

import com.kata.model.Account;

public interface AccountRepository {

    Account getAccount(String idAccount);

    Account save(Account account);
}

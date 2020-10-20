package com.kata.repository;

import com.kata.model.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryMemoryTest {



    @Test(expected = RuntimeException.class)
    public void shouldGetAccountFailedAccountNotExist(){
        AccountRepository accountRepository = new AccountRepositoryMemory();
        accountRepository.getAccount("a552XXCV");
    }

    @Test
    public void shouldGetACreatedAccount(){
        AccountRepository accountRepository = new AccountRepositoryMemory();
        accountRepository.save(Account.builder().id("kjjll452").build());
        Account account = accountRepository.getAccount("kjjll452");
        assertEquals("kjjll452",account.getId());
        assertEquals(0,account.getBalance(),0);
        assertEquals(0,account.getOperations().size());
    }

    @Test
    public void shouldCreatedAccount(){
        AccountRepository accountRepository = new AccountRepositoryMemory();
        Account account = accountRepository.save(Account.builder().id("kjjll452").build());
        assertEquals("kjjll452",account.getId());
        assertEquals(0,account.getBalance(),0);
        assertEquals(0,account.getOperations().size());
    }

}
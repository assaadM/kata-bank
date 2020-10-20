package com.kata.controller;

import com.kata.model.Account;
import com.kata.model.Operation;
import com.kata.model.OperationType;
import com.kata.repository.AccountRepository;
import com.kata.service.AccountService;
import com.kata.service.AccountServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AccountControllerTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountController accountController;

    @Configuration
    static class Config {
        @Bean
        public AccountService getAccountService() {
            return mock(AccountService.class);
        }
        @Bean
        public AccountController getAccountController() {
            return new AccountController();
        }
    }

    @Test
    public void shouldGetAccountByID(){
        Account accountMock = Account.builder().id("a451125").balance(755)
                .operations(new ArrayList<>(Arrays.asList(Operation.builder().amount(500)
                        .type(OperationType.DEPOSIT).build())))
                .build();
        doReturn(accountMock).when(accountService).getAccountStatement("a451125");

        Account result = accountController.get("a451125");

        verify(accountService).getAccountStatement("a451125");
        assertEquals(accountMock  , result);

    }

    @Test
    public void shouldDeposit100euro(){
        accountController.deposit("a451125", 100.05);

        verify(accountService).deposit("a451125", 100.05);

    }

    @Test
    public void shouldWithdrawal100euro(){
        accountController.withdrawal("a451125", 100.05);

        verify(accountService).withdrawal("a451125", 100.05);

    }

    @Test
    public void shouldCreateAccount(){
        accountController.create("a451125");

        verify(accountService).create("a451125");

    }

}
package com.kata.configuration;

import com.kata.repository.AccountRepository;
import com.kata.repository.AccountRepositoryMemory;
import com.kata.service.AccountService;
import com.kata.service.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    AccountRepository accountRepository = new AccountRepositoryMemory();

    @Bean
    public AccountRepository getAccountRepository() {
        return new AccountRepositoryMemory();
    }
    @Bean
    public AccountService getAccountService() {
        return new AccountServiceImpl(accountRepository);
    }

}

package com.kata.service;

import com.kata.model.Account;
import com.kata.model.Operation;
import com.kata.model.OperationType;
import com.kata.repository.AccountRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class AccountServiceTest {

    @Test
    public void shouldDeposit100euro(){
        // GIVEN
        Account accountMock = Account.builder().id("a451125").balance(755)
                .operations(new ArrayList<>(Arrays.asList(Operation.builder().amount(500)
                        .type(OperationType.DEPOSIT).build())))
                .build();
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        doReturn(accountMock).when(accountRepositoryMock).getAccount("a451125");
        AccountService accountService = new AccountServiceImpl(accountRepositoryMock);

        // WHEN
        accountService.deposit("a451125",100);

        // THEN
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(accountArgumentCaptor.capture());
        assertEquals(855 , accountArgumentCaptor.getValue().getBalance(), 0);
        assertEquals(accountMock.getId() , accountArgumentCaptor.getValue().getId());
        assertEquals(2 , accountArgumentCaptor.getValue().getOperations().size());
        assertEquals(100 , accountArgumentCaptor.getValue().getOperations().get(1).getAmount(), 0);
        assertEquals(855  , accountArgumentCaptor.getValue().getOperations().get(1).getBalance(), 0);

    }

    @Test
    public void shouldWithdrawal100euroWithSuccess(){
        // GIVEN
        Account accountMock = Account.builder().id("a451125").balance(755)
                .operations(new ArrayList<>(Arrays.asList(Operation.builder().amount(500)
                        .type(OperationType.DEPOSIT).build())))
                .build();
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        doReturn(accountMock).when(accountRepositoryMock).getAccount("a451125");
        AccountService accountService = new AccountServiceImpl(accountRepositoryMock);

        // WHEN
        accountService.withdrawal("a451125",100);

        // THEN
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(accountArgumentCaptor.capture());
        assertEquals(655 , accountArgumentCaptor.getValue().getBalance(), 0);
        assertEquals(accountMock.getId() , accountArgumentCaptor.getValue().getId());
        assertEquals(2 , accountArgumentCaptor.getValue().getOperations().size());
        assertEquals(100 , accountArgumentCaptor.getValue().getOperations().get(1).getAmount(), 0);
        assertEquals(OperationType.WITH_DRAWL , accountArgumentCaptor.getValue().getOperations().get(1).getType());
        assertEquals(655  , accountArgumentCaptor.getValue().getOperations().get(1).getBalance(), 0);

    }

    @Test
    public void shouldWithdrawal100euroFailedCausedByBalanceLessThanAmount(){
        // GIVEN
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        doReturn(Account.builder().balance(588).build()).when(accountRepositoryMock).getAccount("a451125");
        AccountService accountService = new AccountServiceImpl(accountRepositoryMock);
        // WHEN
        try {
            accountService.withdrawal("a451125", 700);
        } catch(RuntimeException e){
            // THEN
            assertEquals("Operation not authorized: big amount",e.getMessage());
            verify(accountRepositoryMock, times(0)).save(any());
        }


    }

    @Test
    public void shouldGetAccountStatement(){
        // GIVEN
        Account accountMock = Account.builder().id("a451125").balance(755)
                .operations(new ArrayList<>(Arrays.asList(Operation.builder().amount(500)
                        .type(OperationType.DEPOSIT).build())))
                .build();
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        doReturn(accountMock).when(accountRepositoryMock).getAccount("a451125");
        AccountService accountService = new AccountServiceImpl(accountRepositoryMock);
        // WHEN
        Account result = accountService.getAccountStatement("a451125");

        // THEN
        assertEquals(accountMock, result);
        verify(accountRepositoryMock).getAccount("a451125");
    }

    @Test
    public void shouldCreateAccount(){
        // GIVEN
        Account accountMock = Account.builder().id("a451125").balance(755)
                .operations(new ArrayList<>(Arrays.asList(Operation.builder().amount(500)
                        .type(OperationType.DEPOSIT).build())))
                .build();
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        doReturn(accountMock).when(accountRepositoryMock).save(any());
        AccountService accountService = new AccountServiceImpl(accountRepositoryMock);
        // WHEN
        Account result = accountService.create("a451125");

        // THEN
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(accountArgumentCaptor.capture());

        assertEquals("a451125",accountArgumentCaptor.getValue().getId());
        assertNotNull(result.getId());
        assertEquals(accountMock, result);
    }


}

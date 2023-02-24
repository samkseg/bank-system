package com.bank;

import com.bank.service.BankService;
import com.bank.service.impl.BankServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BankMenuTest {

    @Mock
    private BankService bankService;
    BankMenu bankMenu;
    BankAccount bankAccount;

    @BeforeEach
    public void initialize() {
        bankAccount = new BankAccount("Customer", "1");
        bankAccount.setBalance(10000.0);
    }

    @Test
    public void shouldDepositUsingInput() {
        String userInput = "b" + System.getProperty("line.separator")
                + "5000" + System.getProperty("line.separator")
                + "a" + System.getProperty("line.separator")
                + "e";
        InputStream storedInputStream = (new ByteArrayInputStream(userInput.getBytes()));
        System.setIn(storedInputStream);
        bankMenu = new BankMenu(bankService,bankAccount);
        bankMenu.menu();

        verify(bankService, times(1)).deposit(bankAccount, 5000);
    }

    @Test
    public void shouldWithdrawUsingInput() {
        String userInput = "c" + System.getProperty("line.separator")
                + "1000" + System.getProperty("line.separator")
                + "a" + System.getProperty("line.separator")
                + "e";
        InputStream storedInputStream = (new ByteArrayInputStream(userInput.getBytes()));
        System.setIn(storedInputStream);
        bankMenu = new BankMenu(bankService, bankAccount);
        bankMenu.menu();

        verify(bankService, times(1)).withdraw(bankAccount, 1000);

    }

    @Test
    public void shouldHandleRuntimeExceptionWhenWithdrawExceedsBalance() {
        String userInput = "c" + System.getProperty("line.separator")
                + "11000" + System.getProperty("line.separator")
                + "a" + System.getProperty("line.separator")
                + "e";
        InputStream storedInputStream = (new ByteArrayInputStream(userInput.getBytes()));
        System.setIn(storedInputStream);
        bankService = new BankServiceImpl();
        bankMenu = new BankMenu(bankService, bankAccount);
        
        Assertions.assertDoesNotThrow(() -> bankMenu.menu());
    }
}
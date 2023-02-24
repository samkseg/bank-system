package com.bank;

import com.bank.service.BankService;
import com.bank.service.impl.BankServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankServiceImplTest {

    private BankService bankService;
    private BankAccount bankAccount;

    @BeforeEach
    public void initialize() {
        bankService = new BankServiceImpl();
        bankAccount = new BankAccount("Customer", "1");
        bankAccount.setBalance(10000.0);
    }

    @Test
    public void shouldWithdraw(){
        bankService.withdraw(bankAccount, 1000);
        Assertions.assertEquals(9000,bankAccount.getBalance());
    }

    @Test
    public void shouldNotWithdrawWhenInsufficientBalance(){
        bankService.withdraw(bankAccount,20000);
        Assertions.assertEquals(10000, bankAccount.getBalance());
    }

    @Test
    public void shouldDeposit() {
        bankService.deposit(bankAccount,1000);
        Assertions.assertEquals(11000, bankAccount.getBalance());
    }

    @Test
    public void shouldNotDepositNegativeAmount() {
        bankService.deposit(bankAccount, -1000);
        Assertions.assertEquals(10000,bankAccount.getBalance());
    }
}
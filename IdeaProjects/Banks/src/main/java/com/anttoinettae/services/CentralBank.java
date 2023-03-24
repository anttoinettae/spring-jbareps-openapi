package com.anttoinettae.services;

import com.anttoinettae.commands.TransferCommand;
import com.anttoinettae.entities.Bank;
import com.anttoinettae.entities.Client;
import com.anttoinettae.repositories.BankRepository;
import com.anttoinettae.exceptions.*;
import com.anttoinettae.models.*;
import com.anttoinettae.timetools.TimeManager;

import java.math.BigDecimal;

/**
 * God object. Can do literally everything
 */
public class CentralBank {
    public BankRepository banks = new BankRepository();
    public TimeManager timeManager = new TimeManager();


    public Bank addBank(
            String name,
            BigDecimal creditCommission,
            Integer debitInterest,
            Integer smallDepositInterest,
            Integer middleDepositInterest,
            Integer largeDepositInterest) throws InvalidQuantityException, NullNameException {
        Bank newBank = new Bank(
                name,
                creditCommission,
                debitInterest,
                smallDepositInterest,
                middleDepositInterest,
                largeDepositInterest);
        try {
            banks.addBank(newBank);
        } catch (NullBankException | SameBankException ignored){   }

        try {
            timeManager.addSubscriber(newBank);
        } catch (NullBankException ignored){}
        return newBank;
    }

    public void addClient(
            Bank bank, Client client) throws NullIdException, NullClientException, SameClientException, AbsentBankException {
        if (banks.getBankById(bank.id) == null)
            throw new AbsentBankException("u tried to add a client in an absent bank");
        bank.addClient(client);
    }

    public Account addCreditAccountToClient(Bank bank, Client client) throws InvalidQuantityException, NullAccountException, AbsentClientException, NullClientException, AbsentAccountException, SameAccountException {
        return bank.addAccountToClient(new CreditAccount(client, bank.getCreditCommission()), client);
    }

    public Account addDebitAccountToClient(Bank bank, Client client) throws InvalidQuantityException, NullAccountException, AbsentClientException, NullClientException, AbsentAccountException, SameAccountException {
        return bank.addAccountToClient(new DebitAccount(client), client);
    }

    public Account addDepositAccountToClient(Bank bank, Client client) throws InvalidQuantityException, NullAccountException, AbsentClientException, NullClientException, AbsentAccountException, SameAccountException {
        return bank.addAccountToClient(new DepositAccount(client), client);
    }

    public void changeBalance(Account account, BigDecimal howMuch) throws NullAccountException, AbsentAccountException, NotReliableException, InvalidQuantityException {
        Bank accountsBank = banks.getAllBanks().stream().filter(b -> b.accounts.contains(account)).findFirst().orElse(null);
        if (accountsBank == null)
            throw new AbsentAccountException(String.format("u tried to change balance in account %s but no bank contains it", account.getId()));
        accountsBank.changeBalance(account, howMuch);
    }

    public Transaction transferMoney(Account sender, Account receiver, BigDecimal howMuch) throws InvalidQuantityException, NullAccountException, NotReliableException {
        if (sender == null || receiver == null)
            throw new NullAccountException("u tried to transfer money from or to null account");
        var transaction = new Transaction(new TransferCommand(howMuch, sender, receiver));
        transaction.makeTransaction();
        return transaction;
    }

    public void revert(Bank bank, Account account, Transaction transaction) throws NullAccountException, NullTransactionException, AbsentTransactionException, IsCanceledException, NullBankException, AbsentAccountException, InvalidQuantityException {
        if (bank == null)
            throw new NullBankException("u tried to revert transaction in null bank");
        if (!bank.accounts.contains(account))
            throw new AbsentAccountException("u need to give bank that manage this account");
        bank.revertTransaction(account, transaction);
    }

    public void changeBankCommission(Bank bank, BigDecimal newCommission) throws InvalidQuantityException, NullBankException {
        if (bank == null)
            throw new NullBankException("u tried to change commission of a null bank");
        bank.changeCreditCommission(newCommission);
    }

    public void changeBankInterest(Bank bank, int newInterest) throws InvalidQuantityException, NullBankException {
        if (bank == null)
            throw new NullBankException("u tried to change interest of a null bank");
        bank.changeDebitInterest(newInterest);
    }

    public boolean thereIsBank(){
        return banks.getAllBanks().size() != 0;
    }

    public boolean thereIsClient(){
        for (Bank bank : banks.getAllBanks()) {
            if (bank.clients.getAllClients().size() != 0)
                return true;
        }
        return false;
    }

    public boolean thereIsAccount(){
        for (Bank bank : banks.getAllBanks()) {
            if (bank.accounts.getAllAccounts().size() != 0)
                return true;
        }
        return false;
    }

    public boolean thereAreAccounts(){
        int accounts = 0;
        for (Bank bank : banks.getAllBanks()){
            accounts += bank.accounts.getAllAccounts().size();
            if (accounts > 1)
                return true;
        }
        return false;
    }
}

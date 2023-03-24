package com.anttoinettae.repositories;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.*;
import com.anttoinettae.models.Account;
import com.anttoinettae.tools.AccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AccountRepository {
    private List<Account> accounts = new ArrayList<>();

    /**
     *
     * @param account
     * @throws NullAccountException
     * @throws SameAccountException
     */
    public void addAccount(Account account) throws NullAccountException, SameAccountException {
        if (account == null)
            throw new NullAccountException("u tried to add null account to a repository");
        if (accounts.contains(account))
            throw new SameAccountException("u tried to add already existing account to a repository");
        accounts.add(account);
    }

    /**
     *
     * @param accountId
     * @throws NullIdException
     * @throws NullAccountException
     */
    public void removeAccount(UUID accountId) throws NullIdException, NullAccountException, AbsentAccountException {
        if (accountId == null)
            throw new NullIdException("u tried to remove account with null id from a repository");
        Account tempAccount = getAccountById(accountId);
        if (tempAccount == null)
            throw new NullAccountException("u tried to remove null account from a repository");
        this.accounts.remove(tempAccount);
    }

    /**
     *
     * @param accountId
     * @return
     * @throws NullIdException
     */
    public Account getAccountById(UUID accountId) throws NullIdException, AbsentAccountException {
        if (accountId == null)
            throw new NullIdException("u tried to get account with null id in a repository");
        for (Account account : accounts) {
            if(account.getId().equals(accountId)) return account;
        }
        throw new AbsentAccountException("u tried to get an absent account");
    }

    /**
     *
     * @param client
     * @return
     * @throws NullClientException
     */
    public List<Account> getAccountsByClient(Client client) throws NullClientException {
        if (client == null)
            throw new NullClientException("u tried to add null client to a repository");
        List<Account> tempAccounts = new ArrayList<>();
        for (Account account: accounts) {
            if (account.getClient().equals(client)){
                tempAccounts.add(account);
            }
        }
        return tempAccounts;
    }

    /**
     *
     * @return
     */
    public List<Account> getCreditAccounts(){
        List<Account> tempAccounts = new ArrayList<>();
        for (Account account: accounts) {
            if (account.getAccountType() == AccountType.Credit){
                tempAccounts.add(account);
            }
        }
        return tempAccounts;
    }

    /**
     *
     * @return
     */
    public List<Account> getDebitAccounts(){
        List<Account> tempAccounts = new ArrayList<>();
        for (Account account: accounts) {
            if (account.getAccountType() == AccountType.Debit){
                tempAccounts.add(account);
            }
        }
        return tempAccounts;
    }

    /**
     *
     * @return
     */
    public List<Account> getDepositAccounts(){
        List<Account> tempAccounts = new ArrayList<>();
        for (Account account: accounts) {
            if (account.getAccountType() == AccountType.Deposit){
                tempAccounts.add(account);
            }
        }
        return tempAccounts;
    }

    public boolean contains(Account account){
        return accounts.contains(account);
    }
    public List<Account> getAllAccounts(){
        return accounts;
    }
}

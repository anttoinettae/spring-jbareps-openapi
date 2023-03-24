package com.anttoinettae.entities;

import com.anttoinettae.commands.ChangeBalanceCommand;
import com.anttoinettae.models.Account;
import com.anttoinettae.models.Transaction;
import com.anttoinettae.observers.Time.TimeObserver;
import com.anttoinettae.observers.Client.ClientObserver;
import com.anttoinettae.observers.Client.ClientObservable;
import com.anttoinettae.repositories.AccountRepository;
import com.anttoinettae.repositories.ClientRepository;
import com.anttoinettae.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Bank. Questions?
 */
public class Bank implements TimeObserver, ClientObservable {
    public ClientRepository clients = new ClientRepository();
    public AccountRepository accounts = new AccountRepository();
    public List<ClientObserver> subscribers = new ArrayList<>();
    public final UUID id;
    private LocalDate bankCurrentTime;
    public final String name;
    private BigDecimal creditCommission;
    private Integer debitInterest;
    private Integer smallDepositInterest;
    private Integer middleDepositInterest;
    private Integer largeDepositInterest;

    /**
     * @param name                  of the bank
     * @param creditCommission      of the bank
     * @param debitInterest         of the bank
     * @param smallDepositInterest  of the bank
     * @param middleDepositInterest of the bank
     * @param largeDepositInterest  of the bank
     */
    public Bank(
            String name,
            BigDecimal creditCommission,
            Integer debitInterest,
            Integer smallDepositInterest,
            Integer middleDepositInterest,
            Integer largeDepositInterest) throws NullNameException, InvalidQuantityException {

        if (name.isEmpty())
            throw new NullNameException("u tried to create a bank with null name");

        if (creditCommission.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new InvalidQuantityException("u tried to create a bank with negative creadit commission");

        if (debitInterest < 0 || debitInterest > 100 || smallDepositInterest < 0 || smallDepositInterest > 100
            || middleDepositInterest < 0 || middleDepositInterest > 100 || largeDepositInterest < 0 || largeDepositInterest > 100)
            throw new InvalidQuantityException("the interests supposed to be percentage");

        this.id = UUID.randomUUID();
        this.bankCurrentTime = LocalDate.now();
        this.name = name;

        this.creditCommission = creditCommission;
        this.debitInterest = debitInterest;
        this.smallDepositInterest = smallDepositInterest;
        this.middleDepositInterest = middleDepositInterest;
        this.largeDepositInterest = largeDepositInterest;
    }

    /**
     * Add existing client to a bank
     *
     * @param client to add
     * @throws NullClientException if given null client
     * @throws SameClientException if given client that bank already has
     */
    public void addClient(Client client) throws NullClientException, SameClientException {
        if (client == null)
            throw new NullClientException(String.format("u tried to add null client to a %s", this.name));
        if (clients.contains(client))
            throw new SameClientException(String.format("%s already has client with id %s", this.name, client.id.toString()));
        clients.addClient(client);
        addSubscriber(client);
    }

    /**
     * Add existing account to a bank
     *
     * @param account to add
     * @throws NullAccountException if given null account
     * @throws SameAccountException if given an account that bank already has
     */
    private void addAccount(Account account) throws NullAccountException, SameAccountException {
        if (account == null)
            throw new NullAccountException(String.format("u tried to add null account to %s", this.name));
        if (accounts.contains(account))
            throw new SameAccountException(String.format("%s already has account with id %s", this.name, account.getId().toString()));
        accounts.addAccount(account);
    }

    /**
     * Add existing account to a client of the bank
     *
     * @param account to add
     * @param client  to add to
     * @return account that has been added
     * @throws NullAccountException  if given null account
     * @throws NullClientException   if given null client
     * @throws SameAccountException  if given account that already bank has
     * @throws AbsentClientException if given client that bank has not
     */
    public Account addAccountToClient(Account account, Client client) throws NullAccountException, NullClientException, SameAccountException, AbsentClientException {
        if (account == null)
            throw new NullAccountException(String.format("u tried to add null account to a %s client", this.name));
        if (client == null)
            throw new NullClientException(String.format("u tried to add some account to a null client in %s", this.name));
        if (!clients.contains(client)) {
            throw new AbsentClientException(String.format("u tried to add account to an absent client in %s",
                    this.name));
        }
        client.addAccount(account);
        addAccount(account);
        return account;
    }

    /**
     * Changes balance of account of the bank
     *
     * @param account to change balance
     * @param howMuch to change
     * @throws NullAccountException   if given null account
     * @throws AbsentAccountException if given account that isn't in this bank
     */
    public void changeBalance(Account account, BigDecimal howMuch) throws NullAccountException, AbsentAccountException, NotReliableException, InvalidQuantityException {
        if (account == null)
            throw new NullAccountException(String.format("u tried to change null account in %s", this.name));
        if (!accounts.contains(account)) {
            throw new AbsentAccountException(String.format("u tried to change balance of an absent account in %s",
                    this.name));
        }
        Transaction transaction = new Transaction(new ChangeBalanceCommand(howMuch, account));
        transaction.makeTransaction();
    }

    /**
     * Revert a transaction of an account of bank
     *
     * @param account     to revert transaction of
     * @param transaction to revert
     * @throws AbsentTransactionException if given transaction not of that account
     * @throws NullAccountException       if given null account
     * @throws NullTransactionException   if given null transaction
     * @throws IsCanceledException        if given transaction that is already has been canceled
     */
    public void revertTransaction(Account account, Transaction transaction) throws AbsentTransactionException, NullAccountException, NullTransactionException, IsCanceledException, InvalidQuantityException {
        if (account == null)
            throw new NullAccountException(String.format("u tried to revert transaction of a null account in %s", this.name));
        if (transaction == null)
            throw new NullTransactionException(String.format("u tried to revert null transaction in %s", this.name));
        if (!account.getHistory().contains(transaction)) {
            throw new AbsentTransactionException(String.format("u tried to revert transaction that not in the history of %s account. " +
                                                               "With respect, bank %s", account.getId().toString(), this.name));
        }
        if (transaction.getCanceled())
            throw new IsCanceledException("u can't cancel transaction that already have been canceled");
        account.revert(transaction);
        transaction.cancel();
    }

    /**
     * Changes credit commission
     *
     * @param newCommission of the bank
     * @throws InvalidQuantityException if given commission below zero
     */
    public void changeCreditCommission(BigDecimal newCommission) throws InvalidQuantityException {
        if (newCommission.compareTo(new BigDecimal(0)) < 1) {
            throw new InvalidQuantityException(String.format("we won't make credit commission below zero in %s", name));
        }

        creditCommission = newCommission;
        notify("credit commission was changed");
    }

    /**
     * Changes debit interest
     *
     * @param newDebitInterest of the bank
     * @throws InvalidQuantityException if given interest that is not percentage
     */
    public void changeDebitInterest(int newDebitInterest) throws InvalidQuantityException {
        if (newDebitInterest < 0 || newDebitInterest > 100) {
            throw new InvalidQuantityException("debit interest is not percentage");
        }

        debitInterest = newDebitInterest;
        notify("debit interest was changed");
    }

    public void changeSmallDepositInterest(int newSmallDepositInterest) throws InvalidQuantityException {
        if (newSmallDepositInterest < 0 || newSmallDepositInterest > 100) {
            throw new InvalidQuantityException("small deposit interest is not percentage");
        }

        smallDepositInterest = newSmallDepositInterest;
        notify("small deposit interest was changed");
    }

    public void changeMiddleDepositInterest(int newMiddleDepositInterest) throws InvalidQuantityException {
        if (newMiddleDepositInterest < 0 || newMiddleDepositInterest > 100) {
            throw new InvalidQuantityException("middle deposit interest is not percentage");
        }

        middleDepositInterest = newMiddleDepositInterest;
        notify("middle deposit interest was changed");
    }

    public void changeLargeDepositInterest(int newLargeDepositInterest) throws InvalidQuantityException {
        if (newLargeDepositInterest < 0 || newLargeDepositInterest > 100) {
            throw new InvalidQuantityException("small deposit interest is not percentage");
        }

        largeDepositInterest = newLargeDepositInterest;
        notify("large deposit interest was changed");
    }

    /**
     * Add client to subscribers. Allows clients to watch changes in their banks
     *
     * @param subscriber to add
     * @throws NullClientException if given null client
     */
    public void addSubscriber(ClientObserver subscriber) throws NullClientException {
        if (subscriber == null)
            throw new NullClientException(String.format("null subscriber in %s", this.name));
        subscribers.add(subscriber);
    }

    /**
     * Notify clients
     *
     * @param message that clients get
     */
    public void notify(String message) {
        for (ClientObserver subscriber : subscribers) {
            subscriber.notify(message);
        }
    }

    /**
     * Changes the timeline
     *
     * @param howMuchDaysToSkip number of
     */
    public void notify(Integer howMuchDaysToSkip) {
        try {
            for (Account account : accounts.getAllAccounts()) {
                switch (account.getAccountType()) {
                    case Debit:
                        account.changeBalance(BigDecimal.valueOf((long) howMuchDaysToSkip * debitInterest));
                        break;
                    case Deposit:
                        Integer tmpBalance = account.getBalance().intValue();
                        if (account.getBalance().compareTo(new BigDecimal(50000)) < 1) {
                            account.changeBalance(new BigDecimal(this.smallDepositInterest * 0.01 * tmpBalance * howMuchDaysToSkip));
                        } else if (account.getBalance().compareTo(new BigDecimal(100000)) < 1) {
                            account.changeBalance(new BigDecimal(this.middleDepositInterest * 0.01 * tmpBalance * howMuchDaysToSkip));
                        } else {
                            account.changeBalance(new BigDecimal(this.largeDepositInterest * 0.01 * tmpBalance * howMuchDaysToSkip));
                        }
                        break;
                    case Credit:
                        break;
                }
            }

            bankCurrentTime = bankCurrentTime.plusDays(howMuchDaysToSkip);
        } catch (InvalidQuantityException ignored) {
        }
    }

    public BigDecimal getCreditCommission() {
        return creditCommission;
    }
}


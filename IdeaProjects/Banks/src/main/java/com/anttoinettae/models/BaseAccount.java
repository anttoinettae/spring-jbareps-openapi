package com.anttoinettae.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.tools.AccountType;

public abstract class BaseAccount implements Account{
    private List<Transaction> historyOfTransactions = new ArrayList<>();
    private final UUID id;
    protected BigDecimal balance;
    private final Client client;
    private final AccountType accountType;

    /**
     *
     * @param client
     * @param accountType
     * @throws InvalidQuantityException
     */
    protected BaseAccount(Client client, AccountType accountType) throws InvalidQuantityException {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.valueOf(0);
        this.client = client;
        this.accountType = accountType;
    }

    public List<Transaction> getHistory(){
        return List.copyOf(this.historyOfTransactions);
    }

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Client getClient() {
        return this.client;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    /**
     *
     * @param howMuch
     */
    public void changeBalance(BigDecimal howMuch) throws InvalidQuantityException {
        BigDecimal rest = this.balance.add(howMuch);
        if (rest.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new InvalidQuantityException("u can't withdraw more money that this account has");
        this.balance = this.balance.add(howMuch);
    }

    /**
     *
     * @param transaction
     */
    public void revert(Transaction transaction) throws InvalidQuantityException {
        transaction.getCommand().revert();
    }

    /**
     *
     * @param transaction
     */
    public void addToHistory(Transaction transaction){
        this.historyOfTransactions.add(transaction);
    }

    /**
     *
     * @param other
     * @return
     */
    public Boolean equals(BaseAccount other){
        return other != null && id.equals(other.id);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return String.format("%s : %s", this.client.toString(), this.balance.toString());
    }
}

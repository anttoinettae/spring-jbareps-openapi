package com.anttoinettae.models;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.tools.AccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface Account {
    public List<Transaction> getHistory();
    public UUID getId();
    public BigDecimal getBalance();
    public Client getClient();
    public AccountType getAccountType();
    public void changeBalance(BigDecimal howMuch) throws InvalidQuantityException;
    public void revert(Transaction transaction) throws InvalidQuantityException;
    public void addToHistory(Transaction transaction);
    public String toString();
}

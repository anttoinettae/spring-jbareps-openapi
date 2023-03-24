package com.anttoinettae.models;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.tools.AccountType;

import java.math.BigDecimal;

public class CreditAccount extends BaseAccount {
    public BigDecimal commission;

    /**
     *
     * @param client
     * @param commission
     * @throws InvalidQuantityException
     */
    public CreditAccount(Client client, BigDecimal commission) throws InvalidQuantityException {
        super(client, AccountType.Credit);
        this.commission = commission;
    }

    /**
     *
     * @param howMuch
     */
    @Override
    public void changeBalance(BigDecimal howMuch){
        if (balance.add(howMuch).intValue() < 0){
            howMuch = howMuch.add(this.commission.multiply(BigDecimal.valueOf(-1)));
        }

        this.balance = this.balance.add(howMuch);
    }
}

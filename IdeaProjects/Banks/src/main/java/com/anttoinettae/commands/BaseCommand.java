package com.anttoinettae.commands;

import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.exceptions.NotReliableException;
import com.anttoinettae.models.Account;

import java.math.BigDecimal;

/**
 * Abstract class that is used as a pattern command. Works with balances of the accounts
 */
public abstract class BaseCommand implements Command{
    /**
     *
     * @param howMuch is how much money you want this command to operate with
     * @param sender is which account should lose the money
     * @param receiver is which account should get the money
     */
    protected BaseCommand(BigDecimal howMuch, Account sender, Account receiver)
    {
        this.howMuch = howMuch;
        this.sender = sender;
        this.receiver = receiver;
    }

    public final BigDecimal limit = new BigDecimal(1000);
    private BigDecimal howMuch;
    private Account sender;
    private Account receiver;

    /**
     * The method that's responsible for make the transaction
     */
    public void run() throws NotReliableException, InvalidQuantityException {
        if (!this.sender.getClient().reliability() && (howMuch.compareTo(limit) > 0 || howMuch.compareTo(limit.multiply(BigDecimal.valueOf(-1))) < 0))
            throw new NotReliableException("we can't make this transaction, this client is not reliable");
        this.sender.changeBalance(howMuch);
    }

    /**
     * The method that's responsible for revert the transaction
     */
    public void revert() throws InvalidQuantityException {
        this.sender.changeBalance(howMuch.multiply(BigDecimal.valueOf(-1.0)));
    }

    public BigDecimal getHowMuch(){
        return this.howMuch;
    }

    public Account getSender() {
        return this.sender;
    }

    public Account getReceiver() {
        return this.receiver;
    }
}

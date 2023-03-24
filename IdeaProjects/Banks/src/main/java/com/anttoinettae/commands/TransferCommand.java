package com.anttoinettae.commands;

import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.models.Account;

import java.math.BigDecimal;

/**
 * This class realises special command for transactions between two accounts
 */
public class TransferCommand extends BaseCommand {
    /**
     *
     * @param howMuch is how much money you want to transfer
     * @param sender is who loses the money
     * @param receiver is who gets the money
     * @throws InvalidQuantityException when you try to transfer negative amount of money
     */
    public TransferCommand(BigDecimal howMuch, Account sender, Account receiver) throws InvalidQuantityException {
        super(howMuch, sender, receiver);
        if (howMuch.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new InvalidQuantityException("u tried to transfer negative amount of money");
    }

    /**
     * The method that's responsible for make the transaction
     */
    @Override
    public void run() throws InvalidQuantityException {
        this.getSender().changeBalance(this.getHowMuch().multiply(BigDecimal.valueOf(-1)));
        this.getReceiver().changeBalance(this.getHowMuch());
    }

    /**
     * The method that's responsible for revert the transaction
     */
    @Override
    public void revert() throws InvalidQuantityException {
        this.getSender().changeBalance(this.getHowMuch());
        this.getReceiver().changeBalance(this.getHowMuch().multiply(BigDecimal.valueOf(-1)));
    }
}

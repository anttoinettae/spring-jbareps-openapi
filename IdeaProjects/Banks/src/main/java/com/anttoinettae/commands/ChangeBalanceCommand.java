package com.anttoinettae.commands;

import com.anttoinettae.models.Account;

import java.math.BigDecimal;

/**
 * This class is realises special command for transactions that have only one account. For example, operations with cash
 */
public class ChangeBalanceCommand extends BaseCommand{
    /**
     *
     * @param howMuch is how much you want this balance to change
     * @param account is account that needs to be changed
     */
    public ChangeBalanceCommand(BigDecimal howMuch, Account account){
        super(howMuch, account, null);
    }
}

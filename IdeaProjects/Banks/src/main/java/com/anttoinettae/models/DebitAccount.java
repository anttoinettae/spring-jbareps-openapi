package com.anttoinettae.models;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.tools.AccountType;

public class DebitAccount extends BaseAccount {
    /**
     *
     * @param client
     * @throws InvalidQuantityException
     */
    public DebitAccount(Client client) throws InvalidQuantityException {
        super(client, AccountType.Debit);
    }
}

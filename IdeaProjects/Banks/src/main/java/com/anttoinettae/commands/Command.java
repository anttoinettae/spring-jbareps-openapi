package com.anttoinettae.commands;

import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.exceptions.NotReliableException;
import com.anttoinettae.models.Account;

import java.math.BigDecimal;

public interface Command {
    void run() throws NotReliableException, InvalidQuantityException;
    void revert() throws InvalidQuantityException;
    Account getSender();
    Account getReceiver();
    BigDecimal getHowMuch();
}

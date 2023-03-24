package com.anttoinettae.models;

import com.anttoinettae.commands.BaseCommand;
import com.anttoinettae.commands.Command;
import com.anttoinettae.exceptions.InvalidQuantityException;
import com.anttoinettae.exceptions.NotReliableException;

import java.util.UUID;

public class Transaction {
    public final UUID id;
    public final Command command;
    private Boolean isCanceled;

    /**
     *
     * @param command
     */
    public Transaction(BaseCommand command){
        this.id = UUID.randomUUID();
        this.command = command;
        this.isCanceled = false;
    }

    /**
     *
     */
    public void makeTransaction() throws NotReliableException, InvalidQuantityException {
        this.command.run();
        Account sender = this.command.getSender();
        Account receiver = this.command.getReceiver();
        if (sender != null)
            sender.addToHistory(this);
        if (receiver != null)
            receiver.addToHistory(this);
    }

    public UUID getId() {
        return this.id;
    }

    public Command getCommand() {
        return this.command;
    }

    public Boolean getCanceled() {
        return this.isCanceled;
    }
    public void cancel(){ this.isCanceled = true; }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return String.format("%s -> %s : %s", this.command.getSender(), this.command.getReceiver(), this.command.getHowMuch());
    }
}

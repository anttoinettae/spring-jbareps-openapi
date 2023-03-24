package com.anttoinettae.entities;

import com.anttoinettae.exceptions.InvalidPassportException;
import com.anttoinettae.exceptions.NullAccountException;
import com.anttoinettae.exceptions.NullAddressException;
import com.anttoinettae.exceptions.SameAccountException;
import com.anttoinettae.models.Account;
import com.anttoinettae.observers.Client.ClientObserver;
import com.anttoinettae.repositories.AccountRepository;
import com.anttoinettae.tools.CheckPassport;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A person with identity and accounts. Client of the banks
 */
public class Client implements ClientObserver {
    public AccountRepository accounts = new AccountRepository();
    public final UUID id;
    public final String name;
    public final String surname;
    private String passport;
    private String address;
    public List<String> messages = new ArrayList<>();
    public Boolean reliability(){
        return !(this.passport == null) && !this.passport.isEmpty() && !(this.address == null) && !this.address.isEmpty();
    }

    /**
     *
     * @param name of the client
     * @param surname of the client
     * @param passport of the client (optional)
     * @param address of the client (optional)
     */
    public Client(String name, String surname, String passport, String address)
    {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
        this.id = UUID.randomUUID();
    }

    /**
     *
     * @param passport to add
     * @throws InvalidPassportException if given invalid passport
     */
    public void addPassport(String passport) throws InvalidPassportException {
        if (!CheckPassport.check(passport)){
            throw new InvalidPassportException(String.format("u tried to add invalid passport to %s", this.name));
        }
        this.passport = passport;
    }

    /**
     *
     * @param address to add
     * @throws NullAddressException if given null string
     */
    public void addAddress(String address) throws NullAddressException {
        if (address == null || address.isEmpty()){
            throw new NullAddressException(String.format("u tried to add null address to %s", this.name));
        }
        this.address = address;
    }

    /**
     *
     * @param account to add
     * @throws NullAccountException if given null account
     * @throws SameAccountException if given an account that client already has
     */
    public void addAccount(Account account) throws NullAccountException, SameAccountException {
        if (account == null){
            throw new NullAccountException(String.format("u tried to add null account to %s", this.name));
        }
        if (accounts.contains(account)){
            throw new SameAccountException(String.format("%s already has this bank account", this.name));
        }
        accounts.addAccount(account);
    }

    /**
     * Notify client and add message to a pull of client's messages
     * @param message to add
     */
    public void notify(String message){
        messages.add(message);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return String.format("%s %s", this.name, this.surname);
    }
}

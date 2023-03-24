package com.anttoinettae.tools;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.InvalidPassportException;
import com.anttoinettae.exceptions.NullAddressException;
import com.anttoinettae.exceptions.NullNameException;
import com.anttoinettae.exceptions.NullSurnameException;

public class ClientBuilder {
    private String name;
    private String surname;
    private String passport;
    private String address;

    public ClientBuilder withName(String name) throws NullNameException {
        if (name == null || name.isEmpty())
            throw new NullNameException("u tried to create client with null name");
        this.name = name;
        return this;
    }

    public ClientBuilder withSurname(String surname) throws NullSurnameException {
        if (surname == null || surname.isEmpty())
            throw new NullSurnameException("u tried to create client with null surname");
        this.surname = surname;
        return this;
    }

    public ClientBuilder withPassport(String passport) throws InvalidPassportException {
        if (passport == null || passport.isEmpty() || !CheckPassport.check(passport))
            throw new InvalidPassportException(String.format("u tried to add invalid passport to %s", this.name));
        this.passport = passport;
        return this;
    }

    public ClientBuilder withAddress(String address) throws NullAddressException {
        if (address == null || address.isEmpty())
            throw new NullAddressException("u tried to create client with null address");
        this.address = address;
        return this;
    }

    public Client build()
    {
        return new Client(
                this.name,
                this.surname,
                this.passport,
                this.address);
    }
}

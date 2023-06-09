package com.anttoinettae.jdbc;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class JdbcCat {
    @Getter
    @Setter
    private long id;
    public final String name;
    public final Date birth_date;
    public final String breed;
    public final long owner_id;

    public JdbcCat(String name, Date birthDate, String breed, long ownerId){
        this.name = name;
        this.birth_date = birthDate;
        this.breed = breed;
        this.owner_id = ownerId;
    }
}

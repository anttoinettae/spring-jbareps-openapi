package com.anttoinettae.jdbc;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class JdbcOwner {
    @Getter
    @Setter
    private long id;
    public final String name;
    public final Date birth_date;

    public JdbcOwner(String name, Date birthDate){
        this.name = name;
        this.birth_date = birthDate;
    }

    public String getName() {
        return name;
    }

    public Date getBirth_date() {
        return birth_date;
    }
}

package com.anttoinettae.mybatis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class MybatisCat {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    private Date birth_date;

    @Getter
    private String breed;

    @Getter
    @Setter
    private MybatisOwner owner;

    public MybatisCat(String name, Date birth_date, String breed, MybatisOwner owner){
        this.name = name;
        this.birth_date = birth_date;
        this.breed = breed;
        this.owner = owner;
    }
}

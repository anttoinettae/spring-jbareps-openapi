package com.anttoinettae.mybatis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class MybatisOwner {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    private Date birth_date;

    public MybatisOwner(String name, Date birth_date){
        this.name = name;
        this.birth_date = birth_date;
    }
}

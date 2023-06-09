package com.anttoinettae;

import com.anttoinettae.hibernate.HibernateCat;
import com.anttoinettae.hibernate.HibernateManager;
import com.anttoinettae.hibernate.HibernateOwner;
import com.anttoinettae.jdbc.JdbcManager;
import com.anttoinettae.jdbc.JdbcCat;
import com.anttoinettae.jdbc.JdbcOwner;
import com.anttoinettae.mybatis.MybatisCat;
import com.anttoinettae.mybatis.MybatisManager;
import com.anttoinettae.mybatis.MybatisOwner;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {

        JdbcManager manager = new JdbcManager();
        JdbcOwner owner = new JdbcOwner("leonard", Date.valueOf("1998-09-05"));
        manager.save(owner);
        JdbcCat cat = new JdbcCat("buba", Date.valueOf("2009-10-10"), "punk", owner.getId());
        manager.save(cat);
        manager.close();

        HibernateManager hmanager = new HibernateManager();
        HibernateOwner howner = new HibernateOwner("dick", Date.valueOf("2000-01-01"));
        hmanager.save(howner);
        HibernateCat hcat = new HibernateCat("natasha", Date.valueOf("2005-01-01"), "buba", howner);
        hmanager.save(hcat);
        hmanager.close();

        MybatisManager bmanager = new MybatisManager();
        MybatisOwner bowner = new MybatisOwner("weirdo", Date.valueOf("2002-09-09"));
        bmanager.save(bowner);

        System.out.println(bowner.getId());

        MybatisCat bcat = new MybatisCat("girl", Date.valueOf("2015-03-04"), "precious", bowner);
        bmanager.save(bcat);

    }
}
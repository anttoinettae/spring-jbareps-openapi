package com.anttoinettae.mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;


import javax.sql.DataSource;

public class MybatisConfig {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/catsData";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Anta6285471";

    public static SqlSessionFactory buildFactory(){
        DataSource dataSource = new PooledDataSource(DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
        Environment environment = new Environment("Development", new JdbcTransactionFactory(), dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.anttoinettae.mybatis.mappers");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }
}

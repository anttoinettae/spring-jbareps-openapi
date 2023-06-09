package com.anttoinettae.mybatis.mappers;

import com.anttoinettae.mybatis.MybatisOwner;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OwnerMapper {

    @Insert("insert into owner(name, birth_date) values (#{name},#{birth_date})")
    void save(MybatisOwner owner);

    @Delete("Delete from owner where id = #{id}")
    void deleteById(long id);

    @Delete("Delete from owner WHERE name =#{name} AND birth_date =#{birth_date}")
    void deleteByEntity(MybatisOwner owner);

    @Delete("Delete from owner")
    void deleteAll();

    @Update("Update owner set name=#{name}, birth_date=#{birth_date} where id=#{id}")
    void update(MybatisOwner owner);

    @Select("Select * from owner where id=#{id}")
    @Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
            @Result(property = "birth_date", column = "birth_date")
    })
    MybatisOwner getById(long id);

    @Select("select * from owner")
    @ResultType(List.class)
    @Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
            @Result(property = "birth_date", column = "birth_date")
    })
    List<MybatisOwner> getAll();

    @Select("Select id from owner where id=(Select MAX(id) from owner)")
    @Results(value = {@Result(property = "id",column = "id")})
    long getLastId();
}

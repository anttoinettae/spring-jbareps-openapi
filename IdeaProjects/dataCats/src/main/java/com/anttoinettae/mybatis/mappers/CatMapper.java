package com.anttoinettae.mybatis.mappers;

import com.anttoinettae.mybatis.MybatisCat;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CatMapper {
    @Insert("insert into cat(id, name, birth_date, breed, owner_id) values (#{id},#{name},#{birth_date},#{breed},#{owner.id})")
    void save(MybatisCat cat);

    @Delete("Delete from cat where id = #{id}")
    void deleteById(long id);

    @Delete("Delete from cat WHERE cat.name =#{name} AND cat.birth_date =#{birth_date} AND cat.breed =#{breed} AND cat.owner_id =#{owner.id}")
    void deleteByEntity(MybatisCat cat);

    @Delete("Delete from cat")
    void deleteAll();

    @Update("Update cat set name=#{name},owner_id =#{owner.id}, where id=#{id}")
    void update(MybatisCat cat);

    @Select("Select * from cat where id=#{id}")
    @Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
            @Result(property = "birth_date", column = "birth_date"),@Result(property = "breed", column = "breed"),@Result(property = "owner.id", column = "owner_id")
    })
    MybatisCat getById(long id);

    @Select("select * from cat")
    @ResultType(List.class)
    @Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
            @Result(property = "birth_date", column = "birth_date"),@Result(property = "breed", column = "breed"),@Result(property = "owner.id", column = "owner_id")
    })
    List<MybatisCat> getAll();

    @Select("Select * from cat where owner_id=#{id}")
    @Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
            @Result(property = "birth_date", column = "birth_date"),@Result(property = "breed", column = "breed"),@Result(property = "owner.id", column = "owner_id")
    })
    List<MybatisCat> getAllByOwnerId(long id);

    @Select("Select id from cat where id=(Select MAX(id) from cat)")
    @Results(value = {@Result(property = "id",column = "id")})
    long getLastId();
}

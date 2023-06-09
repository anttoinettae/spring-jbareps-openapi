package com.anttoinettae.mybatis;

import com.anttoinettae.mybatis.mappers.CatMapper;
import com.anttoinettae.mybatis.mappers.OwnerMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MybatisManager {

    private SqlSessionFactory sessionFactory;

    public MybatisManager(){
        this.sessionFactory = MybatisConfig.buildFactory();
    }

    public MybatisCat save(MybatisCat cat){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.save(cat);
        cat.setId(catMapper.getLastId());
        session.close();
        return cat;
    }

    public MybatisOwner save(MybatisOwner owner){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.save(owner);
        owner.setId(ownerMapper.getLastId());
        session.close();
        return owner;
    }

    public void deleteCatById(long id){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteById(id);
        session.close();
    }

    public void deleteOwnerById(long id){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteById(id);
        session.close();
    }

    public void deleteByEntity(MybatisCat cat){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteByEntity(cat);
        session.close();
    }

    public void deleteByEntity(MybatisOwner owner){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteByEntity(owner);
        session.close();
    }

    public void deleteAll(){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteAll();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteAll();
        session.close();
    }

    public MybatisCat update(MybatisCat cat){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.update(cat);
        session.close();
        return cat;
    }

    public MybatisOwner update(MybatisOwner owner){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.update(owner);
        session.close();
        return owner;
    }

    public MybatisCat getCatById(long id){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        MybatisCat result = catMapper.getById(id);
        session.close();
        return result;
    }

    public MybatisOwner getOwnerById(long id){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        MybatisOwner result = ownerMapper.getById(id);
        session.close();
        return result;
    }

    public List<MybatisCat> getAllCats(){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        List<MybatisCat> result = catMapper.getAll();
        session.close();
        return result;
    }

    public List<MybatisOwner> getAllOwners(){
        SqlSession session = sessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        List<MybatisOwner> result = ownerMapper.getAll();
        session.close();
        return result;
    }

    public List<MybatisCat> getAllByOwnerId(long ownerId){
        SqlSession session = sessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        List<MybatisCat> result = catMapper.getAllByOwnerId(ownerId);
        session.close();
        return result;
    }
}

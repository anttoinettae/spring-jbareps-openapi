package com.anttoinettae.services;

import com.anttoinettae.entities.Cat;
import com.anttoinettae.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatsService {

    @Autowired
    private CatRepository catRepository;

    @Transactional
    public Cat save(Cat cat){
        catRepository.saveAndFlush(cat);
        return cat;
    }

    @Transactional
    public void deleteById(long id){
        catRepository.deleteById(id);
        catRepository.flush();
    }

    @Transactional
    public void deleteByEntity(Cat cat){
        catRepository.deleteById(cat.getId());
        catRepository.flush();
    }

    @Transactional
    public void deleteAll(){
        catRepository.deleteAll();
        catRepository.flush();
    }

    @Transactional
    public Cat update(Cat cat){
        catRepository.save(cat);
        catRepository.flush();
        return cat;
    }

    @Transactional
    public Cat getById(long id){
        return catRepository.getReferenceById(id);
    }

    @Transactional
    public List<Cat> getAll(){
        return catRepository.findAll();
    }

    @Transactional
    public List<Cat> getAllByOwnerId(long id){return catRepository.findAllByOwnerId(id);}

    @Transactional
    public List<Cat> getAllByName(String name){return catRepository.findAllByName(name);}

}

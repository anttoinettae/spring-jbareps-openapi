package com.anttoinettae.services;

import com.anttoinettae.entities.Cat;
import com.anttoinettae.entities.Owner;
import com.anttoinettae.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnersService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public Owner save(Owner owner){
        ownerRepository.saveAndFlush(owner);
        return owner;
    }

    @Transactional
    public void deleteById(long id){
        ownerRepository.deleteById(id);
        ownerRepository.flush();
    }

    @Transactional
    public void deleteByEntity(Owner owner){
        ownerRepository.deleteById(owner.getId());
        ownerRepository.flush();
    }

    @Transactional
    public void deleteAll(){
        ownerRepository.deleteAll();
        ownerRepository.flush();
    }

    @Transactional
    public Owner update(Owner owner){
        ownerRepository.save(owner);
        ownerRepository.flush();
        return owner;
    }

    @Transactional
    public Owner getById(long id){
        return ownerRepository.getReferenceById(id);
    }

    @Transactional
    public List<Owner> getAll(){
        return ownerRepository.findAll();
    }

    @Transactional
    public List<Owner> getAllByName(String name){return ownerRepository.findAllByName(name);}
}

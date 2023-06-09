package com.anttoinettae.services;

import com.anttoinettae.entities.Flea;
import com.anttoinettae.entities.Owner;
import com.anttoinettae.repositories.FleaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FleasService {

    @Autowired
    private FleaRepository fleaRepository;

    @Transactional
    public Flea save(Flea flea){
        fleaRepository.saveAndFlush(flea);
        return flea;
    }

    @Transactional
    public void deleteById(long id){
        fleaRepository.deleteById(id);
        fleaRepository.flush();
    }

    @Transactional
    public void deleteByEntity(Flea flea){
        fleaRepository.deleteById(flea.getId());
        fleaRepository.flush();
    }

    @Transactional
    public void deleteAll(){
        fleaRepository.deleteAll();
        fleaRepository.flush();
    }

    @Transactional
    public Flea update(Flea flea){
        fleaRepository.save(flea);
        fleaRepository.flush();
        return flea;
    }

    @Transactional
    public Flea getById(long id){
        return fleaRepository.getReferenceById(id);
    }

    @Transactional
    public List<Flea> getAll(){
        return fleaRepository.findAll();
    }

    @Transactional
    public List<Flea> getAllByName(String name){return fleaRepository.findAllByName(name);}
}

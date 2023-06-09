package com.anttoinettae.repositories;

import com.anttoinettae.entities.Cat;
import com.anttoinettae.entities.Flea;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleaRepository extends BaseRepository<Flea, Long>{
    List<Flea> findAllByCatId(long id);
    List<Flea> findAllByName(String name);
}

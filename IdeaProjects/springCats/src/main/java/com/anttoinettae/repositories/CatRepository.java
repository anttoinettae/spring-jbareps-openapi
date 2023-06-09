package com.anttoinettae.repositories;

import com.anttoinettae.entities.Cat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends BaseRepository<Cat, Long> {
    List<Cat> findAllByOwnerId(long id);

    List<Cat> findAllByName(String name);
}

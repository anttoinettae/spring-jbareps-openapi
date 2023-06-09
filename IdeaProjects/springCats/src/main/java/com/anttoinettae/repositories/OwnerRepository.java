package com.anttoinettae.repositories;

import com.anttoinettae.entities.Cat;
import com.anttoinettae.entities.Owner;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends BaseRepository<Owner, Long> {
    List<Owner> findAllByName(String name);
}

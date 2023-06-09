package com.anttoinettae.controllers;

import com.anttoinettae.entities.Owner;
import com.anttoinettae.services.OwnersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "owner")
@RestController
@RequestMapping("/owner")
public class OwnersController {

    @Autowired
    private OwnersService service;

    @PostMapping("/")
    public Owner save(@RequestBody Owner owner){return service.save(owner);}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){service.deleteById(id);}

    @DeleteMapping("/")
    public void deleteAll(){
        service.deleteAll();
    }

    @GetMapping("/{id}")
    public Owner getById(@PathVariable long id){
        return service.getById(id);
    }

    @GetMapping("/owners")
    public List<Owner> getAll(){
        return service.getAll();
    }

    @GetMapping("/byName/{name}")
    List<Owner> getAllByName(@PathVariable String name){
        return service.getAllByName(name);
    }
}

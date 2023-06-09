package com.anttoinettae.controllers;

import com.anttoinettae.entities.Cat;
import com.anttoinettae.services.CatsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "cat")
@RestController
@RequestMapping("/cat")
public class CatsController {

    @Autowired
    private CatsService service;

    @PostMapping("/")
    public Cat save(@RequestBody Cat cat){return service.save(cat);}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){service.deleteById(id);}

    @DeleteMapping("/")
    public void deleteAll(){
        service.deleteAll();
    }

    @GetMapping("/{id}")
    public Cat getById(@PathVariable long id){
        return service.getById(id);
    }

    @GetMapping("/cats")
    public List<Cat> getAll(){
        return service.getAll();
    }

    @GetMapping("/byOwnerId/{id}")
    public List<Cat> getAllByOwnerId(@PathVariable Long id){
        return service.getAllByOwnerId(id);
    }

    @GetMapping("/byName/{name}")
    List<Cat> getAllByName(@PathVariable String name){
        return service.getAllByName(name);
    }

}

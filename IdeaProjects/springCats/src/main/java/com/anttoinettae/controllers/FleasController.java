package com.anttoinettae.controllers;

import com.anttoinettae.entities.Flea;
import com.anttoinettae.services.FleasService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "flea")
@RestController
@RequestMapping("/flea")
public class FleasController {

    @Autowired
    private FleasService service;

    @PostMapping("/")
    public Flea save(@RequestBody Flea flea){return service.save(flea);}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){service.deleteById(id);}

    @DeleteMapping("/")
    public void deleteAll(){
        service.deleteAll();
    }

    @GetMapping("/{id}")
    public Flea getById(@PathVariable long id){
        return service.getById(id);
    }

    @GetMapping("/fleas")
    public List<Flea> getAll(){
        return service.getAll();
    }

    @GetMapping("/byName/{name}")
    List<Flea> getAllByName(@PathVariable String name){
        return service.getAllByName(name);
    }
}

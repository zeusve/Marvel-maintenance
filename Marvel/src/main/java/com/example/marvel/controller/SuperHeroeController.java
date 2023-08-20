package com.example.marvel.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.example.marvel.service.impl.SuperHeroeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.marvel.dto.SuperHeroeDTO;
import com.example.marvel.model.SuperHeroe;
import com.example.marvel.service.SuperHeroeService;

@RestController
@RequestMapping("/api/superheroes")
public class SuperHeroeController {

    private final SuperHeroeServiceImpl service;

    public SuperHeroeController(SuperHeroeServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<SuperHeroeDTO> createSuperHeroe(@RequestBody SuperHeroeDTO superheroe) {
        SuperHeroe newEntity = service.createSuperHeroe(createSuperHeroeFromDTO(superheroe));
        return ResponseEntity.ok(convertSuperHeroetoDTO(newEntity));
    }

    @PostMapping("/added")
    private ResponseEntity<SuperHeroe> guardar(@RequestBody SuperHeroeDTO superheroe) {
        SuperHeroe newEntity = service.createSuperHeroe(createSuperHeroeFromDTO(superheroe));

        try {
            return ResponseEntity.created(new URI("/api/marvel/" + newEntity.getId())).body(newEntity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    private void deleteById(Long id) throws ResourceNotFoundException {
        Boolean delete = false;
        delete = service.deleteId(id);
    }

    /* search superheroe by id */
    @GetMapping("/{id}")
    public SuperHeroeDTO searchId(@PathVariable(required = true) Long id) throws ResourceNotFoundException {
        SuperHeroe superheroeDB = service.findById(id);
        return convertSuperHeroetoDTO(superheroeDB);
    }

    /* search all superheroes in DB */
    @GetMapping("/")
    public List<SuperHeroeDTO> superheroe() throws ResourceNotFoundException {
        List<SuperHeroeDTO> response = new ArrayList<>();
        List<SuperHeroe> superHeroesDB = service.findAllSuperHeroe();
        // CREATE MAPPING SERVICE
        superHeroesDB.forEach(sh -> response.add(convertSuperHeroetoDTO(sh)));
        return response;
    }

    /* search superheroes name contains String params */
    @GetMapping("/name/{name}")
    public List<SuperHeroeDTO> superheroename(@PathVariable(required = true) String name)
            throws ResourceNotFoundException {
        List<SuperHeroeDTO> response = new ArrayList<SuperHeroeDTO>();
        Stream<SuperHeroe> shperh;
        List<SuperHeroe> superHeroesDBName = service.findAllSuperHeroe();
        shperh = superHeroesDBName.stream().filter(sh -> sh.getNombre().contains(name));
        shperh.forEach(sh -> response.add(convertSuperHeroetoDTO(sh)));
        return response;
    }

    /* search superheroes name contains String params */
    @GetMapping("/name/contains/{name}")
    public List<SuperHeroeDTO> supheroename(@PathVariable(required = true) String name)
            throws ResourceNotFoundException {
        List<SuperHeroeDTO> listshDTO = new ArrayList<SuperHeroeDTO>();
        List<SuperHeroe> listsh = service.findByNameContains(name);
        listsh.forEach(sh -> listshDTO.add(convertSuperHeroetoDTO((SuperHeroe) listsh)));
        return listshDTO;
    }

    private SuperHeroe createSuperHeroeFromDTO(SuperHeroeDTO superheroe) {
        return new SuperHeroe(superheroe.getId(), superheroe.getNombre(), superheroe.isLive(), superheroe.getUniverso(),
                superheroe.getPoderes());
    }

    private SuperHeroeDTO convertSuperHeroetoDTO(SuperHeroe superheroe) {
        return new SuperHeroeDTO(superheroe.getId(), superheroe.getNombre(), superheroe.isLive(),
                superheroe.getUniverso(), superheroe.getPoderes());

    }

}

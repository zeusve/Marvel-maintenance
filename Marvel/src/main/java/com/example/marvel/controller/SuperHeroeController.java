package com.example.marvel.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.example.marvel.exception.ErrorDetails;
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

    private final SuperHeroeService service;

    @Autowired
    public SuperHeroeController(SuperHeroeService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> createSuperHeroe(@RequestBody SuperHeroeDTO superheroe) {
        try {
            SuperHeroe newEntity = service.createSuperHeroe(createSuperHeroeFromDTO(superheroe));
            return ResponseEntity.created(new URI("/api/superheroes/" + newEntity.getId())).body(newEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorDetails(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorDetails(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            boolean deleteSuccess = service.deleteId(id);

            if (deleteSuccess) {
                return ResponseEntity.ok("SuperHeroe deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDetails(e.getMessage()));
        }
    }

    /* search superheroe by id */
    @GetMapping("/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) {
        try {
            SuperHeroe superheroeDB = service.findById(id);
            return ResponseEntity.ok(convertSuperHeroetoDTO(superheroeDB));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDetails(e.getMessage()));
        }
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
    public List<SuperHeroeDTO> superheroename(@PathVariable String name)
            throws ResourceNotFoundException {
        List<SuperHeroeDTO> response = new ArrayList<>();
        Stream<SuperHeroe> shperh;
        List<SuperHeroe> superHeroesDBName = service.findAllSuperHeroe();
        shperh = superHeroesDBName.stream().filter(sh -> sh.getNombre().contains(name));
        shperh.forEach(sh -> response.add(convertSuperHeroetoDTO(sh)));
        return response;
    }

    /* search superheroes name contains String params */
    @GetMapping("/name/contains/{name}")
    public List<SuperHeroeDTO> supheroename(@PathVariable String name)
            throws ResourceNotFoundException {
        List<SuperHeroeDTO> listshDTO = new ArrayList<>();
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

    private ErrorDetails createErrorDetails(String details) {
        return new ErrorDetails(new Date(), "Resource not found", details);
    }

}

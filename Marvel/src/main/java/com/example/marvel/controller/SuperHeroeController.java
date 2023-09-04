package com.example.marvel.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.example.marvel.exception.ErrorDetails;
import com.example.marvel.exception.ErrorModel400;
import com.example.marvel.model.Universo;
import com.example.marvel.service.UniversoService;
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
    private final UniversoService universoService;
    private final List<ErrorDetails> errorDetails = new ArrayList<>();
    @Autowired
    public SuperHeroeController(SuperHeroeService service, UniversoService universoService) throws ErrorModel400 {
        this.service = service;
        this.universoService = universoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createSuperHeroe(@RequestBody SuperHeroeDTO superheroe) {
        try {
            validateSuperHeroe(superheroe);
            SuperHeroe newEntity = service.createSuperHeroe(createSuperHeroeFromDTO(superheroe));
            return ResponseEntity.created(new URI("/api/superheroes/" + newEntity.getId())).body(newEntity);
        } catch (ResourceNotFoundException | IllegalArgumentException e){
            errorDetails.clear();
            errorDetails.add(createErrorDetails(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorModel400(errorDetails).getErrors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorDetails(e.getMessage()));
        } catch (ErrorModel400 e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrors());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(createErrorDetails("Invalid ID: " + id));
        }

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
    public ResponseEntity<?> superheroe() {
        try {
            List<SuperHeroeDTO> response = new ArrayList<>();
            List<SuperHeroe> superHeroesDB = service.findAllSuperHeroe();
            superHeroesDB.forEach(sh -> response.add(convertSuperHeroetoDTO(sh)));
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDetails(e.getMessage()));
        }
    }

    /* search superheroes name contains String params */
    @GetMapping("/name/{name}")
    public ResponseEntity<?> superheroename(@PathVariable String name) {
        try {
            List<SuperHeroeDTO> response = new ArrayList<>();
            Stream<SuperHeroe> superHeroeStream;
            List<SuperHeroe> superHeroesDBName = service.findAllSuperHeroe();
            superHeroeStream = superHeroesDBName.stream().filter(sh -> sh.getNombre().contains(name));
            superHeroeStream.forEach(sh -> response.add(convertSuperHeroetoDTO(sh)));
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDetails(e.getMessage()));
        }
    }

    /* search superheroes name contains String params */
    @GetMapping("/name/contains/{name}")
    public ResponseEntity<?> supheroename(@PathVariable String name) {
        try {
            List<SuperHeroeDTO> superHeroeDTO = new ArrayList<>();
            List<SuperHeroe> byNameContains = service.findByNameContains(name);
            byNameContains.forEach(sh -> superHeroeDTO.add(convertSuperHeroetoDTO((SuperHeroe) byNameContains)));
            return ResponseEntity.ok(superHeroeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorDetails(e.getMessage()));
        }
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

    private void validateSuperHeroe(SuperHeroeDTO sh) throws ErrorModel400 {
        errorDetails.clear();
        if (sh == null) {
            errorDetails.add(new ErrorDetails(new Date(), "SuperHeroe cannot be null",""));
        }
        if (sh != null) {
            if (sh.getNombre() == null) {
                errorDetails.add(new ErrorDetails(new Date(), "Name is required mandatory",""));
            }
        }
        Universo universe = sh.getUniverso();

        if (universe == null) {
            errorDetails.add(new ErrorDetails(new Date(), "Universe is required mandatory", ""));
        } else {
            Long universeId = universe.getId();
            if (universeId == null) {
                errorDetails.add(new ErrorDetails(new Date(), "Universe id is required mandatory", ""));
            } else {
                Universo universo = universoService.findById(universeId);
                if (universo.getId()==null){
                    errorDetails.add(new ErrorDetails(new Date(), "Universe id does not exist", ""));
                }
            }
        }

        if (!errorDetails.isEmpty()) {
            throw new ErrorModel400(errorDetails);
        }
    }
}

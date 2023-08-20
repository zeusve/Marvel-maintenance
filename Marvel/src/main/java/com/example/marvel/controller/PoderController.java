package com.example.marvel.controller;

import com.example.marvel.dto.UniversoDTO;
import com.example.marvel.model.Universo;
import com.example.marvel.service.impl.PoderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marvel.dto.PoderDTO;
import com.example.marvel.model.Poder;
import com.example.marvel.service.PoderService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/power")
public class PoderController {

    private final PoderServiceImpl service;

    public PoderController(PoderServiceImpl service) {
        this.service = service;
    }

    /* list all superheroes */
    @GetMapping("/")
    public List<PoderDTO> poderDTO() throws ResourceNotFoundException {
        List<PoderDTO> response = new ArrayList<>();
        List<Poder> poderDB = service.findAllUniversos();
        // CREATE MAPPING SERVICE
        poderDB.forEach(u -> response.add(convertPoderToDTO(u)));
        return response;
    }

    /* search superheroe by id */
    @GetMapping("/{id}")
    public PoderDTO searchId(@PathVariable(required = true) Long id) throws ResourceNotFoundException {
        Poder poderDB = service.findById(id);
        return convertPoderToDTO(poderDB);
    }

    private PoderDTO convertPoderToDTO(Poder poderDB) {
        return new PoderDTO(poderDB.getId(), poderDB.getNombre());
    }
}

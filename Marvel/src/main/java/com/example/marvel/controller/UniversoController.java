package com.example.marvel.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.marvel.service.UniversoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marvel.dto.UniversoDTO;
import com.example.marvel.model.Universo;

@RestController
@RequestMapping("/api/universe")
public class UniversoController {

    private final UniversoService service;

    @Autowired
    public UniversoController(UniversoService service){
        this.service = service;
    }

    /* search superheroe by id */
    @GetMapping("/{id}")
    public UniversoDTO searchId(@PathVariable Long id) throws ResourceNotFoundException {
        Universo universoDB = service.findById(id);
        // CREATE MAPPING SERVICE
        return convertPoderToDTO(universoDB);
    }

    @GetMapping("/")
    public List<UniversoDTO> universo() throws ResourceNotFoundException {
        List<UniversoDTO> response = new ArrayList<>();
        List<Universo> universosDB = service.findAllUniversos();
        // CREATE MAPPING SERVICE
        universosDB.forEach(u -> response.add(convertPoderToDTO(u)));
        return response;
    }

    private UniversoDTO convertPoderToDTO(Universo universoDB) {
        return new UniversoDTO(universoDB.getId(), universoDB.getNombre());
    }

}

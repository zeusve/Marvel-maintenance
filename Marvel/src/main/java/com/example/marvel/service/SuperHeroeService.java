package com.example.marvel.service;

import java.util.List;

import com.example.marvel.exception.ErrorDetails;
import com.example.marvel.exception.ErrorModel400;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.example.marvel.model.SuperHeroe;

public interface SuperHeroeService {
    SuperHeroe createSuperHeroe(SuperHeroe newSuperHeroe) throws ErrorModel400;

    /*Search all super heroes in DB*/
    List<SuperHeroe> findAllSuperHeroe() throws ResourceNotFoundException;

    /*search super hero by id*/
    SuperHeroe findById(Long id) throws ResourceNotFoundException;

    /*create superheroe*/
    void save(SuperHeroe sh);

    /*list all super heroes contains %name%*/
    List<SuperHeroe> findByNameContains(String name) throws ResourceNotFoundException;

    /*modified superheroe*/
    SuperHeroe update(SuperHeroe sh) throws ResourceNotFoundException;

    /*delete superheroe*/
    Boolean deleteId(Long id) throws ResourceNotFoundException;
}
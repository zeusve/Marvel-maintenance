package com.example.marvel.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import com.example.marvel.model.Poder;

import java.util.List;

public interface PoderService {

    /* search super hero by id */
    Poder findById(Long id) throws ResourceNotFoundException;

    List<Poder> findAllUniversos();
}

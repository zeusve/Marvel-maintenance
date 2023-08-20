package com.example.marvel.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import com.example.marvel.model.Universo;

public interface UniversoService {

    Universo findById(Long id) throws ResourceNotFoundException;

    List<Universo> findAllUniversos();

}

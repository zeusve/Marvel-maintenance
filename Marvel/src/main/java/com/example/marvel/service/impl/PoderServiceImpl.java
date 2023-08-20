package com.example.marvel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.marvel.model.Poder;
import com.example.marvel.repository.PoderRepository;
import com.example.marvel.service.PoderService;

@Service
public class PoderServiceImpl implements PoderService {

    @Autowired
    PoderRepository repository;

    @Override
    public List<Poder> findAllUniversos() throws ResourceNotFoundException {
        List<Poder> poderFromDB = repository.findAll();
        if (poderFromDB.isEmpty()) {
            throw new ResourceNotFoundException("not found");
        }
        return poderFromDB;
    }

    @Override
    public Poder findById(Long id) throws ResourceNotFoundException {
        Optional<Poder> poderDB = repository.findById(id);
        if (poderDB.isEmpty()) {
            throw new ResourceNotFoundException("Poder not found");
        }
        return poderDB.get();
    }

}

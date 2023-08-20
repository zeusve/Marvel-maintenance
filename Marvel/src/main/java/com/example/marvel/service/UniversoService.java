package com.example.marvel.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.marvel.model.Universo;

public interface UniversoService {

	Universo findById(Long id) throws ResourceNotFoundException;

	List<Universo> findAllUniversos();

}

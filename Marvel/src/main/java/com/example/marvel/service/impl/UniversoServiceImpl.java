package com.example.marvel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.marvel.model.Universo;
import com.example.marvel.repository.UniversoRepository;
import com.example.marvel.service.UniversoService;

@Service
@Primary
public class UniversoServiceImpl implements UniversoService {

	private final UniversoRepository repository;

	public UniversoServiceImpl(UniversoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Universo findById(Long id) throws ResourceNotFoundException {
		Optional<Universo> universoDB = repository.findById(id);
		if (universoDB.isEmpty()) {
			throw new ResourceNotFoundException("Poder not found");
		}
		return universoDB.get();
	}
	@Override
	public List<Universo> findAllUniversos() throws ResourceNotFoundException {
		List<Universo> universoFromDB = repository.findAll();
		if (universoFromDB.isEmpty()) {
			throw new ResourceNotFoundException("not found");
		}
		return universoFromDB;
	}

}

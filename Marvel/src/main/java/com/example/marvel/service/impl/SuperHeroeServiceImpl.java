package com.example.marvel.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.marvel.model.SuperHeroe;
import com.example.marvel.repository.SuperHeroeRepository;
import com.example.marvel.service.SuperHeroeService;

@Service
public class SuperHeroeServiceImpl implements SuperHeroeService {

	@Autowired
	SuperHeroeRepository repository;

	@Override
	public SuperHeroe createSuperHeroe(SuperHeroe sh) {
		if (sh != null)
			repository.save(sh);
		else
			throw new ResourceNotFoundException("super heroe is null");
		return sh;
	}

	@Override
	public SuperHeroe update(SuperHeroe sh) {
		repository.findById(sh.getId()).ifPresent(sh1 -> {
			sh1.setNombre(sh.getNombre());
			sh1.setLive(sh.isLive());
			sh1.setUniverso(sh.getUniverso());
			sh1.setPoderes(sh.getPoderes());
			if (sh1.equals(sh))
				repository.save(sh1);
			else
				throw new ResourceNotFoundException("the superheroe is null");
		});
		return sh;
	}

	@Override
	public Boolean deleteId(Long id) throws ResourceNotFoundException {
		repository.deleteById(id);
		return null;
	}

	@Override
	public void save(SuperHeroe sh) {
		if (sh == null) {
			throw new ResourceNotFoundException("the superheroe is null");
		}
		repository.save(sh);
	}

	@Override
	public List<SuperHeroe> findAllSuperHeroe() throws ResourceNotFoundException {
		List<SuperHeroe> superheroeFromDB = (List<SuperHeroe>) repository.findAll();
		if (superheroeFromDB.isEmpty()) {
			throw new ResourceNotFoundException("no superheroes found in the database!");
		}
		return superheroeFromDB;
	}

	@Override
	public SuperHeroe findById(Long id) throws ResourceNotFoundException {
		Optional<SuperHeroe> superheroeDB = repository.findById(id);
		if (superheroeDB.get().getId() != id) {
			throw new ResourceNotFoundException("There is no hero with the id entered in the database!");
		}
		return superheroeDB.get();
	}

	public List<SuperHeroe> findByNameContains(String name) {
		List<SuperHeroe> shList = repository.findByNombreContains(name);
		if (shList.isEmpty())
			throw new ResourceNotFoundException("there are no results for the indicated name");
		return shList;

	}

}

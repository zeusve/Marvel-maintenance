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

    private final SuperHeroeRepository superHeroeRepository;

    @Autowired
    public SuperHeroeServiceImpl(SuperHeroeRepository superHeroeRepository) {
        this.superHeroeRepository = superHeroeRepository;
    }

    @Override
    public SuperHeroe createSuperHeroe(SuperHeroe sh) {
        List<SuperHeroe> existingHeroes = superHeroeRepository.findByNombreContains(sh.getNombre());
        if (!existingHeroes.isEmpty()) {
            throw new ResourceNotFoundException("A SuperHeroe with the same name already exists");
        }
        return superHeroeRepository.save(sh);
    }

    @Override
    public SuperHeroe update(SuperHeroe sh) {
        superHeroeRepository.findById(sh.getId()).ifPresent(sh1 -> {
            sh1.setNombre(sh.getNombre());
            sh1.setLive(sh.isLive());
            sh1.setUniverso(sh.getUniverso());
            sh1.setPoderes(sh.getPoderes());
            if (sh1.equals(sh))
                superHeroeRepository.save(sh1);
            else
                throw new ResourceNotFoundException("the superheroe is null");
        });
        return sh;
    }

    @Override
    public Boolean deleteId(Long id) throws ResourceNotFoundException {
        if (superHeroeRepository.existsById(id)) {
            superHeroeRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("SuperHeroe with ID " + id + " not found");
        }
    }

    @Override
    public void save(SuperHeroe sh) {
        if (sh == null) {
            throw new ResourceNotFoundException("the superheroe is null");
        }
        superHeroeRepository.save(sh);
    }

    @Override
    public List<SuperHeroe> findAllSuperHeroe() throws ResourceNotFoundException {
        List<SuperHeroe> superheroeFromDB = superHeroeRepository.findAll();
        if (superheroeFromDB.isEmpty()) {
            throw new ResourceNotFoundException("no superheroes found in the database!");
        }
        return superheroeFromDB;
    }

    @Override
    public SuperHeroe findById(Long id) throws ResourceNotFoundException {
        Optional<SuperHeroe> superheroeDB = superHeroeRepository.findById(id);
        if (superheroeDB.isEmpty()) {
            throw new ResourceNotFoundException("There is no hero with the id entered in the database!");
        }

        return superheroeDB.get();
    }

    public List<SuperHeroe> findByNameContains(String name) {
        List<SuperHeroe> shList = superHeroeRepository.findByNombreContains(name);
        if (shList.isEmpty())
            throw new ResourceNotFoundException("there are no results for the indicated name");
        return shList;
    }
}

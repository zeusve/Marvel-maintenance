package com.example.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.marvel.model.Poder;

@Repository
public interface PoderRepository extends JpaRepository<Poder, Long>{

}

package com.example.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.marvel.model.Universo;

@Repository
public interface UniversoRepository extends JpaRepository<Universo, Long> {

}

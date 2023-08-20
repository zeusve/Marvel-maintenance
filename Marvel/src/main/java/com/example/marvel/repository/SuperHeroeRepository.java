package com.example.marvel.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.marvel.model.SuperHeroe;

@Repository
public interface SuperHeroeRepository extends JpaRepository<SuperHeroe, Long> {

	/* search superheroes name equal string parameter */
	List<SuperHeroe> findByNombreContains(String name);

}

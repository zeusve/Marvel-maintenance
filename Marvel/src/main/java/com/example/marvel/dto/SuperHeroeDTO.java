package com.example.marvel.dto;

import java.io.Serializable;
import java.util.Set;

import com.example.marvel.model.Poder;
import com.example.marvel.model.Universo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SuperHeroeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private boolean live;
    private Universo universo;
    private Set<Poder> poderes;

}

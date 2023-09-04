package com.example.marvel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SuperHeroe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotNull(message = "name cannot be null")
    private String nombre;

    @Column(name = "live")
    private boolean live;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Universo universo;

    @ManyToMany
    private Set<Poder> poderes = new HashSet<>();
}

package com.example.springandroid.Repositorios;

import com.example.springandroid.Entidades.JuegoEntity;
import com.example.springandroid.Entidades.JugadorEntity;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepositorio extends JpaRepository<JugadorEntity, Integer> {
   JugadorEntity getById(int id);
    JugadorEntity getByedad(Integer ruta);

    boolean existsByEdad(Integer ruta);
}


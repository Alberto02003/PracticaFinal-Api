package com.example.springandroid.Repositorios;

import com.example.springandroid.Entidades.JuegoEntity;
import com.example.springandroid.Entidades.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuegoRepositorio extends JpaRepository<JuegoEntity, Integer> {
    JuegoEntity getByNombre(String nombre);

    JuegoEntity getById(int id);

    boolean existsByRuta(String ruta);
}

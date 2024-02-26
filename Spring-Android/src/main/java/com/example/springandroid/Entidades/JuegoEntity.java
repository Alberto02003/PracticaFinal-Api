package com.example.springandroid.Entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "juego", schema = "juegos", catalog = "")
public class JuegoEntity {
    @JsonManagedReference
    @OneToMany(mappedBy = "juegoById", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<JuegoEntity> JuegoId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "plataforma")
    private String plataforma;
    @OneToMany(mappedBy = "juegoById")
    private Collection<JugadorEntity> jugadorsById;

    public Collection<JuegoEntity>getId() {
        return JuegoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JuegoEntity that = (JuegoEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (genero != null ? !genero.equals(that.genero) : that.genero != null) return false;
        if (plataforma != null ? !plataforma.equals(that.plataforma) : that.plataforma != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (plataforma != null ? plataforma.hashCode() : 0);
        return result;
    }

    public Collection<JugadorEntity> getJugadorsById() {
        return jugadorsById;
    }

    public void setJugadorsById(Collection<JugadorEntity> jugadorsById) {
        this.jugadorsById = jugadorsById;
    }
}

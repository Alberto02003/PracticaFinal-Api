package com.example.springandroid.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "jugador", schema = "juegos", catalog = "")
public class JugadorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "edad")
    private Integer edad;
    @ManyToOne
    @JoinColumn(name = "juego_id", referencedColumnName = "id")
    private JuegoEntity juegoByJuegoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JugadorEntity that = (JugadorEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (edad != null ? !edad.equals(that.edad) : that.edad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (edad != null ? edad.hashCode() : 0);
        return result;
    }

    public JuegoEntity getJuegoByJuegoId() {
        return juegoByJuegoId;
    }

    public void setJuegoByJuegoId(JuegoEntity juegoByJuegoId) {
        this.juegoByJuegoId = juegoByJuegoId;
    }
}

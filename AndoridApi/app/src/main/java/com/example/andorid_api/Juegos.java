package com.example.andorid_api;

import java.util.ArrayList;

class JuegosById{
    public int id;
    public String nombre;
}

public class Juegos{
    public int id;
    public String nombre;
    public String genero;
    public String plataforma;
    public ArrayList<JuegosById> conductorsById;
    public Juegos(String nombre, String genero, String plataforma){
        this.nombre = nombre;
        this.genero = genero;
        this.plataforma = plataforma;
    }
}
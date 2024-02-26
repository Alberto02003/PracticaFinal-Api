package com.example.springandroid.Controlador;

import com.example.springandroid.Entidades.JugadorEntity;
import com.example.springandroid.Entidades.StringReponse;
import com.example.springandroid.Repositorios.JugadorRepositorio;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Jugador")
public class JugadorControlador {
    @Autowired
    JugadorRepositorio repositorio;

    @GetMapping("")
    List<JugadorEntity> getAllGuaguas(){
        return repositorio.findAll();
    }
    @GetMapping("/{id}")
    JugadorEntity getGuaguasById(@PathVariable("id") Integer id){

        return repositorio.getById(id);
    }
    @GetMapping("/rutas")
    JugadorEntity getGuaguasByName(@PathParam("ruta") Integer ruta ){
        return repositorio.getByedad(ruta);

    }
    @PostMapping("")
    ResponseEntity<?> postGuagua(@Valid @RequestBody JugadorEntity nuevoArtista ){
        if(repositorio.existsById(nuevoArtista.getId())){
            return new ResponseEntity<>(new StringReponse("Ya hay un juego con esa id"), HttpStatus.CONFLICT);
        }else{
            JugadorEntity artistaGuardado = repositorio.save(nuevoArtista);
            return new ResponseEntity<>(artistaGuardado, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGuagua(@PathVariable ("id") Integer idRecibida){
        if(repositorio.existsById(idRecibida)){
            String nombreArtista = repositorio.getById(idRecibida).getNombre();
            repositorio.deleteById(idRecibida);
            return new ResponseEntity<>(new StringReponse("Se ha borrado al conductor " + nombreArtista), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new StringReponse("No hay un conductor con esa ID"), HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> putArtista(@PathVariable ("id") Integer idRecibida, @Valid @RequestBody JugadorEntity viejoArtista){
        if(repositorio.existsById(idRecibida)){
            JugadorEntity  ArtistaEncontrado = repositorio.getById(idRecibida);
            String nombreArtista = ArtistaEncontrado.getNombre();
            ArtistaEncontrado.setNombre(viejoArtista.getNombre());

            repositorio.save(ArtistaEncontrado);

            return new ResponseEntity<>(new StringReponse("Se ha actualizado el conductor ") + nombreArtista, HttpStatus.OK);

        }else{

            return new ResponseEntity<>(new StringReponse("No hay un conductor con esa ID"), HttpStatus.CONFLICT);

        }
    }
}

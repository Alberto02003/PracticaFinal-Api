package com.example.springandroid.Controlador;

import com.example.springandroid.Entidades.JuegoEntity;
import com.example.springandroid.Entidades.JugadorEntity;
import com.example.springandroid.Entidades.StringReponse;
import com.example.springandroid.Repositorios.JuegoRepositorio;
import com.example.springandroid.Repositorios.JugadorRepositorio;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Juegos")
public class JuegoControlador {
    @Autowired
    JugadorRepositorio JugadorRepositorio;
    @Autowired
    JuegoRepositorio juegoRepository;
    @GetMapping("")
    List<JuegoEntity> getAllJuego(){
        return juegoRepository.findAll();
    }
    @GetMapping("/{id}")
    JuegoEntity getGuaguasById(@PathVariable("id") Integer id){

        return juegoRepository.getById(id);
    }
    @GetMapping("/nombre")
    JuegoEntity getJuegoByName(@PathParam("nombre") String nombre ){
        return juegoRepository.getByNombre(nombre);

    }
    @PostMapping("")
    ResponseEntity<?> postGuagua(@Valid @RequestBody JuegoEntity juego ){
        if(juegoRepository.existsByRuta(juego.getNombre())){
            return new ResponseEntity<>(new StringReponse("Ya hay una juego con ese nombre"), HttpStatus.CONFLICT);
        }else{
            JuegoEntity artistaGuardado = juegoRepository.save(juego);
            return new ResponseEntity<>(artistaGuardado, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteJuego(@PathVariable ("id") Integer idRecibida){
        if(juegoRepository.existsById(idRecibida)){
            String nombreArtista = juegoRepository.getById(idRecibida).getNombre();
            juegoRepository.deleteById(idRecibida);
            return new ResponseEntity<>(new StringReponse("Se ha borrado el juego " + nombreArtista), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new StringReponse("No hay una juego con esa ID"), HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> putJuego(@PathVariable ("id") Integer idRecibida, @Valid @RequestBody JuegoEntity juegoAntiguo){
        if(juegoRepository.existsById(idRecibida)){
            JuegoEntity  ArtistaEncontrado = juegoRepository.getById(idRecibida);
            String nombreArtista = ArtistaEncontrado.getNombre();
            ArtistaEncontrado.setNombre(juegoAntiguo.getNombre());

            juegoRepository.save(ArtistaEncontrado);

            return new ResponseEntity<>(new StringReponse("Se ha actualizado el juego " + nombreArtista), HttpStatus.OK);

        }else{

            return new ResponseEntity<>(new StringReponse("No hay un juego con esa ID"), HttpStatus.CONFLICT);

        }
    }
    @PutMapping("/deleteJuego/{id}")
    ResponseEntity<?> deleteJuegoJugador(@PathVariable ("id") Integer idRecibida, @PathParam("id") Integer idReview){

        if(juegoRepository.existsById(idRecibida) && JugadorRepositorio.existsById(idReview)){
            JuegoEntity  ArtistaEncontrado = juegoRepository.getById(idRecibida);
            String nombreArtista = ArtistaEncontrado.getNombre();
            JuegoEntity review = juegoRepository.getById(idReview);
            review.getId().remove(ArtistaEncontrado);
            ArtistaEncontrado.setJugadorsById(null);
            juegoRepository.save(ArtistaEncontrado);
            return new ResponseEntity<>(new StringReponse("Se ha añadido al juego " + nombreArtista + ", ya no está en el jugador: " + review.getNombre()), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(new StringReponse("No hay un juego o jugaddor con esa ID"), HttpStatus.CONFLICT);

        }
    }

}


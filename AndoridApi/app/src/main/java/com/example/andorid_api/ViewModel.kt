package com.example.andorid_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andorid_api.GuaguaApi.retrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class estadoApi{
    IDLE, LOADING, SUCCESS, ERROR
}
class ViewModelRetrofit : ViewModel() {
    private val _listaGuaguas : MutableStateFlow<List<Juegos>?> = MutableStateFlow(null)
    var listaGuaguas= _listaGuaguas.asStateFlow()


    private val _estadoLlamada : MutableStateFlow<estadoApi> = MutableStateFlow(estadoApi.IDLE)
    var estadoLlamada = _estadoLlamada.asStateFlow()

    private val _textoBusqueda : MutableStateFlow<String> = MutableStateFlow("")
    var textoBusqueda = _textoBusqueda.asStateFlow()

    fun actualizarTextoBusqueda(s: String) { _textoBusqueda.value = s }

    private val _activo = MutableStateFlow(false)
    var activo = _activo.asStateFlow()

    fun actualizarActivo(b : Boolean) {_activo.value = b}
    init {
        obtenerJuegos()
    }
    fun obtenerJuegos(){
        _estadoLlamada.value = estadoApi.LOADING
        viewModelScope.launch {
            var respuesta = retrofitService.getAllJuego()
            if(respuesta.isSuccessful){
                _listaGuaguas.value = respuesta.body()
                _estadoLlamada.value = estadoApi.SUCCESS
                println(respuesta.body().toString())
            }
            else println("Ha habido algún error " + respuesta.errorBody())



        }
    }
    fun eliminarJuego(id: Int) {
        _estadoLlamada.value = estadoApi.LOADING
        viewModelScope.launch {
            try {
                val response = retrofitService.deleteJuego(id)
                if (response.isSuccessful) {
                    obtenerJuegos()
                    println("Se eliminó la guagua con éxito")
                    _estadoLlamada.value = estadoApi.SUCCESS
                } else {
                    println("Ha habido algún error al eliminar: ${response.errorBody()}")
                    _estadoLlamada.value = estadoApi.ERROR
                }
            } catch (e: Exception) {
                println("Ha habido algún error: ${e.message}")
                _estadoLlamada.value = estadoApi.ERROR
            }
        }
    }

    fun actualizarjuego(id: Int, juego: Juegos) {
        _estadoLlamada.value = estadoApi.LOADING
        viewModelScope.launch {
            try {
                val response = retrofitService.updateJuego(id, juego)
                if (response.isSuccessful) {
                    obtenerJuegos()
                    println("Se actualizó el Juego con éxito")
                    _estadoLlamada.value = estadoApi.SUCCESS
                } else {
                    println("Ha habido algún error al actualizar: ${response.errorBody()}")
                    _estadoLlamada.value = estadoApi.ERROR
                }
            } catch (e: Exception) {
                println("Ha habido algún error: ${e.message}")
                _estadoLlamada.value = estadoApi.ERROR
            }
        }
    }
    fun postJuego(juego: Juegos){
        _estadoLlamada.value = estadoApi.LOADING
        viewModelScope.launch {
            try {
                val response = retrofitService.createJuego( juego)
                if (response.isSuccessful) {
                    obtenerJuegos()
                    println("Se creó el juego con éxito")
                    _estadoLlamada.value = estadoApi.SUCCESS
                } else {
                    println("Ha habido algún error al crear: ${response.errorBody()}")
                    _estadoLlamada.value = estadoApi.ERROR
                }
            } catch (e: Exception) {
                println("Ha habido algún error: ${e.message}")
                _estadoLlamada.value = estadoApi.ERROR
            }
        }
    }

}
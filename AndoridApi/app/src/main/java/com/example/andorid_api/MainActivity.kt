package com.example.andorid_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.andorid_api.ui.theme.AndoridApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndoridApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel : ViewModelRetrofit = viewModel()
                    val estado by viewModel.estadoLlamada.collectAsState()
                    val listaJuegos by viewModel.listaGuaguas.collectAsState()
                    if(estado == estadoApi.LOADING){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
                            Text(
                                text = "Cargando Titsa",
                                fontSize = 18.sp
                            )
                        }
                    }else{
                        Column() {
                            var openDialog by remember { mutableStateOf(false) }
                            Button(onClick = {
                                openDialog = true
                            }) {
                                Text(text = "Crear Guagua")
                            }
                            if(openDialog){
                                dialogoCrear(viewModel = viewModel) {
                                    openDialog = false
                                }
                            }
                            LazyColumn(){
                                items(listaJuegos!!){
                                    ItemGuagua(a = it, viewModel = viewModel)
                                }
                            }

                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ItemGuagua(a:Juegos, viewModel: ViewModelRetrofit){
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        dialogoModificar(a = a, viewModel = viewModel){
            openDialog = false
        }

    }

    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        modifier = Modifier.clickable {
            openDialog = true
        }
    ) {
        Row(Modifier.fillMaxWidth()) {
            var imageSize by remember { mutableStateOf(Size.Zero) }
            AsyncImage(model = a.imagenRuta, contentDescription = null, modifier =
            Modifier
                .size(200.dp)
                .wrapContentWidth(
                    if (imageSize.width < 400) Alignment.CenterHorizontally else
                        Alignment.Start
                )
                .onSizeChanged { imageSize = it.toSize() }, contentScale = ContentScale.Fit,
                alignment = Alignment.Center)
            println("Image size: ${imageSize.width} x ${imageSize.height}")
            a.ruta?.let { Text(text = "Línea: " + it, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally, true),
                textAlign = TextAlign.Center) }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogoModificar(a: Juegos, viewModel: ViewModelRetrofit,onCloseDialog: () -> Unit){
    var nuevaRuta by remember { mutableStateOf(a.ruta) } // Variable para almacenar el nuevo valor del TextField
    var nuevaImagen by remember { mutableStateOf(a.imagenRuta) }
    AlertDialog(
        onDismissRequest = {
            onCloseDialog()

        },
        title = {
            Text(text = "Ruta: " + a.ruta)
        },
        text = {
            Column {
                Text("Opciones de modificación:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nuevaRuta,
                    onValueChange = { nuevaRuta = it },
                    label = { Text("Cambiar Ruta") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nuevaImagen,
                    onValueChange = { nuevaImagen = it },
                    label = { Text("Cambiar Imagen") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    a.ruta = nuevaRuta
                    a.imagenRuta = nuevaImagen
                    viewModel.actualizarjuego(a.id, a)
                    onCloseDialog()

                }) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.eliminarJuego(a.id)
                    onCloseDialog()

                }) {
                Text("Borrar")
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogoCrear( viewModel: ViewModelRetrofit,onCloseDialog: () -> Unit){
    var nuevaRuta by remember { mutableStateOf("") } // Variable para almacenar el nuevo valor del TextField
    var nuevaImagen by remember { mutableStateOf("") }
    val a = Juegos(nuevaRuta, nuevaImagen )

    AlertDialog(
        onDismissRequest = {
            onCloseDialog()

        },
        title = {
            Text(text = "Crear ruta ")
        },
        text = {
            Column {
                Text("Opciones de modificación:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nuevaRuta,
                    onValueChange = { nuevaRuta = it },
                    label = { Text("Cambiar Ruta") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nuevaImagen,
                    onValueChange = { nuevaImagen = it },
                    label = { Text("Cambiar Imagen") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    a.ruta = nuevaRuta
                    a.imagenRuta = nuevaImagen
                    viewModel.postJuego(a)
                    onCloseDialog()

                }) {
                Text("Crear")
            }
        },
        dismissButton = {
            Button(
                onClick = {

                    onCloseDialog()

                }) {
                Text("Salir")
            }
        }
    )
}
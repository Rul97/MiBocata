package com.example.mibocadillo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class PerfilUsuario : AppCompatActivity() {
    private lateinit var usuario_logueado: Usuario
    private lateinit var nombre: TextView
    private lateinit var correo: TextView
    private lateinit var telefono: TextView
    private lateinit var direccion: TextView
    private lateinit var perfil: ImageView
    private lateinit var pedir: ImageView
    private lateinit var historial: ImageView

    private fun cargarUsuarios():List<Usuario>{
        val inputStream = resources.openRawResource(R.raw.usuarios)
        val reader= InputStreamReader(inputStream)
        val type = object : TypeToken<List<Usuario>>(){}.type
        return Gson().fromJson(reader, type)
    }
    private fun getUsuario(usuarios: List<Usuario>, correo_usuario: String): Usuario?{
        // Filtrar la lista de bocadillos por el día de la semana
        return usuarios.find { it.correo == correo_usuario }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_usuario)

        val usuario_correo = intent.getStringExtra("usuario_correo") ?: ""
        val usuarios_registrados = cargarUsuarios()

        // Usar '!!' para lanzar una excepción si no se encuentra el usuario
        usuario_logueado = getUsuario(usuarios_registrados, usuario_correo)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        nombre=findViewById(R.id.nombre_usuario)
        correo=findViewById(R.id.correo_usuario)
        telefono=findViewById(R.id.telefono)
        direccion=findViewById(R.id.direccion)

        nombre.text=usuario_logueado.nombre
        correo.text=usuario_logueado.correo
        direccion.text=usuario_logueado.direccion
        telefono.text=usuario_logueado.telefono

        //Navegacion entre apartados de la aplicación
        perfil=findViewById(R.id.perfil)
        pedir=findViewById(R.id.menu_inicio)
        historial=findViewById(R.id.historial)

        perfil.setOnClickListener{
            val intent = Intent(this, PerfilUsuario::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }
        pedir.setOnClickListener{
            val intent = Intent(this, PedirBocadillos::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }
        historial.setOnClickListener{
            val intent = Intent(this, HistorialPedidos::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }

    }
}

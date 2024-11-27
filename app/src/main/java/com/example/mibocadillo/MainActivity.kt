package com.example.mibocadillo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var mibtn: Button
    private lateinit var usuario: EditText
    private lateinit var pass: EditText

    // Función para cargar los usuarios desde el archivo JSON
    private fun cargarUsuarios(): List<Usuario> {
        val inputStream = resources.openRawResource(R.raw.usuarios) // Nombre del archivo JSON
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Usuario>>() {}.type

        return Gson().fromJson(reader, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        mibtn = findViewById(R.id.button)
        usuario = findViewById(R.id.editTextText)
        pass = findViewById(R.id.editTextTextPassword)

        mibtn.setOnClickListener {
            if (usuario.text.isEmpty() || pass.text.isEmpty()) {
                Toast.makeText(this, "Usuario o Contraseña vacio", Toast.LENGTH_LONG).show()
            } else {
                val usuarios:List<Usuario> = cargarUsuarios()

                    val usuarioValido = usuarios.find {
                        it.correo == usuario.text.toString() && it.contrasena == pass.text.toString()
                    }
                    // Verificación de si el usuario ha sido encontrado
                    if (usuarioValido == null) {
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    } else {
                        val intent = Intent(this, PedirBocadillos::class.java)
                        intent.putExtra("usuario_correo",usuario.text.toString())
                        startActivity(intent)
                    }
            }
        }
    }
}

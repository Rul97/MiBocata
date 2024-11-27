package com.example.mibocadillo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.util.Calendar

class PedirBocadillos : AppCompatActivity() {
    private lateinit var caliente : ShapeableImageView
    private lateinit var frio : ShapeableImageView
    private lateinit var perfil:ImageView
    private lateinit var pedir:ImageView
    private lateinit var historial:ImageView

    private fun cargarBocadillos():List<Bocadillo>{
        val inputStream = resources.openRawResource(R.raw.bocadillos)
        val reader=InputStreamReader(inputStream)
        val type = object : TypeToken<List<Bocadillo>>(){}.type
        return Gson().fromJson(reader, type)
    }
    private fun getBocadilloDelDia(bocadillos: List<Bocadillo>, dayOfWeek: Int, tipo: String): Bocadillo? {
        // Filtrar la lista de bocadillos por el día de la semana
        return bocadillos.find { it.diaDeLaSemana == dayOfWeek && it.tipo ==tipo}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedir_bocadillo)

        //Correo del usuario logeado
        val usuario_correo = intent.getStringExtra("usuario_correo")
        // Comprobacion por log del correo que llega
        //Log.d("LoginPedidos", "Correo recibido: $usuario_correo")

        // Obtener el día de la semana
        val calendar = Calendar.getInstance()
        val dia_de_la_semana = calendar.get(Calendar.DAY_OF_WEEK) // Domingo = 1, Sábado = 7

        val bocadillos_calientes = cargarBocadillos()
        val bocadillos_frios = cargarBocadillos()

        // Buscar los bocadillos que corresponden al día de la semana
        val bocadillo_caliente_del_dia = getBocadilloDelDia(bocadillos_calientes, dia_de_la_semana,"Caliente")
        val bocadillo_frio_del_dia = getBocadilloDelDia(bocadillos_frios, dia_de_la_semana, "Frio")

        // Mostrar solo un ítem según el día de la semana
        val listViewCalientes = findViewById<ListView>(R.id.lista_bocadillos_calientes)
        val adapterCalientes = BocadilloAdapter(this, listOfNotNull(bocadillo_caliente_del_dia))
        listViewCalientes.adapter = adapterCalientes

        val listViewFrios = findViewById<ListView>(R.id.lista_bocadillos_frios)
        val adapterFrios = BocadilloAdapter(this, listOfNotNull(bocadillo_frio_del_dia))
        listViewFrios.adapter = adapterFrios

        //Navegacion al pulsar en los contenedores de los bocadillos
        caliente=findViewById(R.id.contenedor_bocadillo_caliente)
        frio=findViewById(R.id.contenedor_bocadillo_frio)

        caliente.setOnClickListener{
            val intent = Intent(this, HistorialPedidos::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }
        frio.setOnClickListener{
            val intent = Intent(this, HistorialPedidos::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }

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
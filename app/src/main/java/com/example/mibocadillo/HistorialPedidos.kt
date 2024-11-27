package com.example.mibocadillo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class HistorialPedidos : AppCompatActivity() {
    lateinit var bocadillos_calientes:TextView
    lateinit var bocadillos_frios:TextView
    private lateinit var perfil: ImageView
    private lateinit var pedir: ImageView
    private lateinit var menu_historial: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pedidos)

        //Correo del usuario logeado
        val usuario_correo = intent.getStringExtra("usuario_correo")
        // Comprobacion por log del correo que llega
        //Log.d("HistorialPedidos", "Correo recibido: $usuario_correo")


        // Cargar el historial de pedidos
        val historial: List<Pedido> = cargarHistorialPedidos()
        //Comprobacion por log del listado de pedidos
        //Log.d("HistorialPedidos", "Pedidos cargados: $historial")
        val pedidosFiltrados = historial.filter { it.usuarioCorreo.trim().equals(usuario_correo?.trim(), ignoreCase = true) }


        // Verificar si el usuario tiene pedidos
        if (pedidosFiltrados.isNotEmpty()) {
            // Mostrar los pedidos en un ListView
            val listView = findViewById<ListView>(R.id.lista_historial_pedidos)
            val adapter = PedidoAdapter(this, pedidosFiltrados)
            listView.adapter = adapter
        } else {
            // Si el usuario no tiene pedidos
            Toast.makeText(this, "No tienes pedidos en el historial.", Toast.LENGTH_SHORT).show()
        }

        //Calculo de bocadillos por tipos que tiene el usuario logueado
        val total_bocadillos_frios = contarBocadillosTipo(pedidosFiltrados,"Frio", usuario_correo)
        //Log.d("total frio", "bocadillos frios: $total_bocadillos_frios")

        val total_bocadillos_calientes = contarBocadillosTipo(pedidosFiltrados,"Caliente", usuario_correo)
        //Log.d("total caliente", "bocadillos calientes: $total_bocadillos_calientes")

        bocadillos_calientes=findViewById(R.id.total_calientes)
        bocadillos_frios=findViewById(R.id.total_frios)

        bocadillos_frios.text=total_bocadillos_frios.toString()
        bocadillos_calientes.text=total_bocadillos_calientes.toString()


        //Navegacion entre apartados de la aplicación
        perfil=findViewById(R.id.perfil)
        pedir=findViewById(R.id.menu_inicio)
        menu_historial=findViewById(R.id.historial)

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
        menu_historial.setOnClickListener{
            val intent = Intent(this, HistorialPedidos::class.java)
            intent.putExtra("usuario_correo", usuario_correo)
            startActivity(intent)
        }
    }

    //Función para cargar el historial de pedidos
    private fun cargarHistorialPedidos():List<Pedido>{
        val inputStream = resources.openRawResource(R.raw.pedidos)
        val reader= InputStreamReader(inputStream)
        val type = object : TypeToken<List<Pedido>>(){}.type
        return Gson().fromJson(reader, type)
    }

    // Función para contar los bocadillos de un tipo específico
    private fun contarBocadillosTipo(pedidos:List<Pedido>, bocadilloTipo: String, usuario_correo: String?): Int {
        val total_tipo_pedidos:Int
        total_tipo_pedidos = pedidos.count(){it.bocadillo.tipo==bocadilloTipo && it.usuarioCorreo == usuario_correo}
        Log.d("Total filtrado","bocadillos $bocadilloTipo : $total_tipo_pedidos")
        return total_tipo_pedidos
    }
}

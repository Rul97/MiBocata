package com.example.mibocadillo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PedidoAdapter(private val context: Context, pedidos: List<Pedido>) : ArrayAdapter<Pedido>(context, 0, pedidos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_historial_pedido, parent, false)

        val pedido = getItem(position)
        // Mostrar los datos del pedido en los elementos correspondientes
        val textViewBocadillo = view?.findViewById<TextView>(R.id.text_bocadillo)
        val textViewFecha = view?.findViewById<TextView>(R.id.text_fecha)

        textViewBocadillo?.text = pedido?.bocadillo?.nombre // Asegúrate de que el objeto Bocadillo tiene un nombre
        textViewFecha?.text = pedido?.fecha

        return view!!
    }
}

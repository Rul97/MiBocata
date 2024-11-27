package com.example.mibocadillo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class BocadilloAdapter(context: Context, bocadillos: List<Bocadillo>) : ArrayAdapter<Bocadillo>(context, 0, bocadillos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bocadillo = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_bocadillo, parent, false)
        val nombreTextView: TextView = view.findViewById(R.id.nombreBocadillo)
        val descripcionTextView: TextView = view.findViewById(R.id.descripcionBocadillo)
        val ingredientesTextView: TextView = view.findViewById(R.id.ingredientesBocadillo)

        nombreTextView.text = bocadillo?.nombre
        descripcionTextView.text = bocadillo?.descripcion
        ingredientesTextView.text= bocadillo?.ingredientes

        return view
    }
}

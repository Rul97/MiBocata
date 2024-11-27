package com.example.mibocadillo

data class Bocadillo (
    val dia:String,
    val nombre:String,
    val descripcion :String,
    val ingredientes:String,
    val tipo:String,
    val diaDeLaSemana: Int // Esto representa el día de la semana (1 = Domingo, 2 = Lunes, ..., 7 = Sábado)

)
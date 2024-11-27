package com.example.mibocadillo

data class Pedido(
    val usuarioCorreo: String,
    val bocadillo: Bocadillo,
    val fecha: String
)
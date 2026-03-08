package com.example.techaudit.network

data class EquipoApiModel(

    val id: String? = null,
    val nombre: String,
    val ubicacion: String,
    val fechaRegistro: String,
    val estado: String,
    val notas: String,
    val fotoUri: String?,
    val laboratorioId: String

)
package com.example.techaudit.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // enviar laboratorio
    @POST("laboratorios")
    suspend fun enviarLaboratorio(
        @Body laboratorio: LaboratorioApiModel
    ): Response<LaboratorioApiModel>

    // enviar equipo
    @POST("equipos")
    suspend fun enviarEquipo(
        @Body equipo: EquipoApiModel
    ): Response<EquipoApiModel>

}
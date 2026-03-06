package com.example.techaudit.model

import androidx.room.Embedded
import androidx.room.Relation

data class LaboratorioConEquipos(

    @Embedded
    val laboratorio: Laboratorio,

    @Relation(
        parentColumn = "id",
        entityColumn = "laboratorioId"
    )
    val equipos: List<AuditItem>

)
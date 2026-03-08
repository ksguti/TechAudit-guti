package com.example.techaudit.data

import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

class LaboratorioRepository(
    private val laboratorioDao: LaboratorioDao
) {

    val allLaboratorios: Flow<List<Laboratorio>> =
        laboratorioDao.getAll()

    suspend fun insert(lab: Laboratorio) {
        laboratorioDao.insert(lab)
    }

    suspend fun update(lab: Laboratorio) {
        laboratorioDao.update(lab)
    }

    suspend fun delete(lab: Laboratorio) {
        laboratorioDao.delete(lab)
    }
}
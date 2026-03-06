package com.example.techaudit.data

import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

class AuditRepository(
    private val auditDao: AuditDao,
    private val laboratorioDao: LaboratorioDao
) {


    // EQUIPOS
    val allItems: Flow<List<AuditItem>> = auditDao.getAllItems()

    suspend fun insert(item: AuditItem) {
        auditDao.insert(item)
    }

    suspend fun update(item: AuditItem) {
        auditDao.update(item)
    }

    suspend fun delete(item: AuditItem) {
        auditDao.delete(item)
    }

    fun getItemsByLaboratorio(labId: Int): Flow<List<AuditItem>> {
        return auditDao.getItemsByLaboratorio(labId)
    }

    suspend fun getItemById(id: String): AuditItem? {
        return auditDao.getById(id)
    }


    // ==========================
    // LABORATORIOS
    // ==========================

    val allLaboratorios: Flow<List<Laboratorio>> =
        laboratorioDao.getAll()

    suspend fun insertLaboratorio(lab: Laboratorio) {
        laboratorioDao.insert(lab)
    }

    suspend fun updateLaboratorio(lab: Laboratorio) {
        laboratorioDao.update(lab)
    }

    suspend fun deleteLaboratorio(lab: Laboratorio) {
        laboratorioDao.delete(lab)
    }

    suspend fun getFirstLaboratorio(): Laboratorio? {
        return laboratorioDao.getFirstLaboratorio()
    }
}
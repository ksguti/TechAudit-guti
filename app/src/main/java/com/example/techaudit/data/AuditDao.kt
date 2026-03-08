package com.example.techaudit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.techaudit.model.AuditItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditDao {

    // =========================
    // EQUIPOS
    // =========================

    // Obtener todos los equipos
    @Query("SELECT * FROM equipos ORDER BY fechaRegistro DESC")
    fun getAllItems(): Flow<List<AuditItem>>

    // Obtener equipos de un laboratorio específico
    @Query("""
        SELECT * FROM equipos 
        WHERE laboratorioId = :labId 
        ORDER BY fechaRegistro DESC
    """)
    fun getItemsByLaboratorio(labId: Int): Flow<List<AuditItem>>

    // Buscar equipo por ID
    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getById(id: String): AuditItem?

    @Query("SELECT * FROM equipos")
    suspend fun getAllItemsDirect(): List<AuditItem>

    // Insertar equipo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AuditItem)

    // Actualizar equipo
    @Update
    suspend fun update(item: AuditItem)

    // Eliminar equipo
    @Delete
    suspend fun delete(item: AuditItem)
}
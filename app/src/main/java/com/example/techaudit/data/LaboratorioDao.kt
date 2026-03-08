package com.example.techaudit.data

import androidx.room.*
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

@Dao
interface LaboratorioDao {

    @Query("SELECT * FROM laboratorios")
    suspend fun getAllLaboratoriosDirect(): List<Laboratorio>

    @Query("SELECT * FROM laboratorios ORDER BY nombre ASC")
    fun getAll(): Flow<List<Laboratorio>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lab: Laboratorio): Long

    @Update
    suspend fun update(lab: Laboratorio)

    @Delete
    suspend fun delete(lab: Laboratorio)

    @Query("SELECT * FROM laboratorios LIMIT 1")
    suspend fun getFirstLaboratorio(): Laboratorio?
}
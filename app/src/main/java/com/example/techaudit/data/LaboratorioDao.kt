package com.example.techaudit.data

import androidx.room.*
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

@Dao
interface LaboratorioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(laboratorio: Laboratorio): Long

    @Update
    suspend fun update(laboratorio: Laboratorio)

    @Delete
    suspend fun delete(laboratorio: Laboratorio)

    @Query("SELECT * FROM laboratorios ORDER BY id DESC")
    fun getAll(): Flow<List<Laboratorio>>

    @Query("SELECT * FROM laboratorios LIMIT 1")
    suspend fun getFirstLaboratorio(): Laboratorio?
}
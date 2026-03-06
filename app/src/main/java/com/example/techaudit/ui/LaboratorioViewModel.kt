package com.example.techaudit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.data.LaboratorioRepository
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.launch

class LaboratorioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LaboratorioRepository

    val allLaboratorios: LiveData<List<Laboratorio>>

    init {
        val dao = AuditDatabase.getDatabase(application).laboratorioDao()
        repository = LaboratorioRepository(dao)

        allLaboratorios = repository.allLaboratorios.asLiveData()
    }

    fun insert(laboratorio: Laboratorio) = viewModelScope.launch {
        repository.insert(laboratorio)
    }

    fun update(laboratorio: Laboratorio) = viewModelScope.launch {
        repository.update(laboratorio)
    }

    fun delete(laboratorio: Laboratorio) = viewModelScope.launch {
        repository.delete(laboratorio)
    }
}
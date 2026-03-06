package com.example.techaudit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.techaudit.TechAuditApp
import com.example.techaudit.data.AuditRepository
import com.example.techaudit.model.AuditItem
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuditRepository

    init {

        val db = (application as TechAuditApp).database

        val auditDao = db.auditDao()
        val laboratorioDao = db.laboratorioDao()

        repository = AuditRepository(auditDao, laboratorioDao)
    }

    // Obtener equipos de un laboratorio
    fun getItemsByLaboratorio(labId: Int): LiveData<List<AuditItem>> {
        return repository.getItemsByLaboratorio(labId).asLiveData()
    }

    fun delete(item: AuditItem) = viewModelScope.launch {
        repository.delete(item)
    }
}
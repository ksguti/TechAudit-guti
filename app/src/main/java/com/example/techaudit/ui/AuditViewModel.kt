package com.example.techaudit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.data.AuditRepository
import com.example.techaudit.model.AuditItem
import kotlinx.coroutines.launch

class AuditViewModel(
    application: Application,
    private val laboratorioId: Int
) : AndroidViewModel(application) {

    private val repository: AuditRepository

    val itemsByLaboratorio: LiveData<List<AuditItem>>

    init {

        val database = AuditDatabase.getDatabase(application)

        val auditDao = database.auditDao()
        val laboratorioDao = database.laboratorioDao()

        repository = AuditRepository(auditDao, laboratorioDao)

        itemsByLaboratorio =
            repository.getItemsByLaboratorio(laboratorioId).asLiveData()
    }

    fun delete(item: AuditItem) = viewModelScope.launch {
        repository.delete(item)
    }

    fun insert(item: AuditItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: AuditItem) = viewModelScope.launch {
        repository.update(item)
    }
}
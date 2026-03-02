package com.example.techaudit.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuditViewModelFactory(
    private val application: Application,
    private val laboratorioId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuditViewModel(application, laboratorioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.techaudit

import android.app.Application
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TechAuditApp : Application() {

    // Lazy: la base de datos se crea solo cuando se necesita
    val database by lazy { AuditDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        // Inicialización en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            val laboratorioDao = database.laboratorioDao()

            val laboratorioExistente = laboratorioDao.getFirstLaboratorio()

            if (laboratorioExistente == null) {
                laboratorioDao.insert(
                    Laboratorio(
                        nombre = "Laboratorio General",
                        edificio = "Edificio Principal"
                    )
                )
            }
        }
    }
}
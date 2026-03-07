package com.example.techaudit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.techaudit.databinding.ActivityAddEditLaboratorioBinding
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.launch

class AddEditLaboratorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditLaboratorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditLaboratorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarLaboratorio.setOnClickListener {
            guardarLaboratorio()
        }
    }

    private fun guardarLaboratorio() {

        val nombre = binding.etNombreLaboratorio.text.toString()
        val edificio = binding.etEdificio.text.toString()

        if (nombre.isEmpty() || edificio.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val laboratorio = Laboratorio(
            nombre = nombre,
            edificio = edificio
        )

        lifecycleScope.launch {

            val database = (application as TechAuditApp).database

            database.laboratorioDao().insert(laboratorio)

            Toast.makeText(this@AddEditLaboratorioActivity, "Laboratorio guardado", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}
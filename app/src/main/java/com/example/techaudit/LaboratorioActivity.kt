package com.example.techaudit

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techaudit.adapter.LaboratorioAdapter
import com.example.techaudit.databinding.ActivityLaboratorioBinding
import com.example.techaudit.ui.LaboratorioViewModel

class LaboratorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaboratorioBinding

    private lateinit var adapter: LaboratorioAdapter

    private val viewModel: LaboratorioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaboratorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()

        viewModel.allLaboratorios.observe(this) { lista ->
            adapter.actualizarLista(lista)
        }
    }

    private fun setupRecycler() {

        adapter = LaboratorioAdapter(mutableListOf()) { laboratorioSeleccionado ->

            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("LAB_ID", laboratorioSeleccionado.id)

            startActivity(intent)
        }

        binding.rvLaboratorios.layoutManager = LinearLayoutManager(this)

        binding.rvLaboratorios.adapter = adapter
    }
}
package com.example.techaudit

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.techaudit.adapter.LaboratorioAdapter
import com.example.techaudit.databinding.ActivityLaboratorioBinding
import com.example.techaudit.model.Laboratorio
import com.example.techaudit.ui.LaboratorioViewModel
import kotlinx.coroutines.launch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
class LaboratorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaboratorioBinding
    private lateinit var adapter: LaboratorioAdapter
    private val viewModel: LaboratorioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaboratorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearLaboratorioInicialSiNoExiste()

        setupRecycler()
        setupSwipeDelete()

        viewModel.allLaboratorios.observe(this) { lista ->
            adapter.actualizarLista(lista)
        }
        binding.fabAgregarLaboratorio.setOnClickListener {

            val intent = Intent(this, AddEditLaboratorioActivity::class.java)
            startActivity(intent)

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

    private fun setupSwipeDelete() {

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.bindingAdapterPosition
                val laboratorio = adapter.obtenerLaboratorio(position)

                lifecycleScope.launch {

                    val database = (application as TechAuditApp).database
                    database.laboratorioDao().delete(laboratorio)

                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvLaboratorios)
    }
    private fun crearLaboratorioInicialSiNoExiste() {

        val database = (application as TechAuditApp).database

        lifecycleScope.launch {

            val repo = database.laboratorioDao()

            val primerLab = repo.getFirstLaboratorio()

            if (primerLab == null) {

                val laboratorio = Laboratorio(
                    nombre = "Lab 1",
                    edificio = "Edificio A"
                )

                repo.insert(laboratorio)
            }
        }
    }
}
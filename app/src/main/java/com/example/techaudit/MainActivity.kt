package com.example.techaudit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.adapter.AuditAdapter
import com.example.techaudit.databinding.ActivityMainBinding
import com.example.techaudit.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AuditAdapter
    private val viewModel: MainViewModel by viewModels()

    private var laboratorioId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        laboratorioId = intent.getIntExtra("LAB_ID", -1)
        Toast.makeText(this, "LAB ID recibido: $laboratorioId", Toast.LENGTH_LONG).show()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        configurarDeslizarParaBorrar()

        // Verificar que exista laboratorio
        if (laboratorioId != -1) {

            Toast.makeText(this, "Laboratorio seleccionado: $laboratorioId", Toast.LENGTH_SHORT).show()

            viewModel.getItemsByLaboratorio(laboratorioId)
                .observe(this) { listaActualizada ->
                    adapter.actualizarLista(listaActualizada)
                }
        }

        binding.fabAgregar.setOnClickListener {

            val intent = Intent(this, AddEditActivity::class.java)

            // enviamos el laboratorio al formulario
            intent.putExtra("LAB_ID", laboratorioId)

            startActivity(intent)
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {

        adapter = AuditAdapter(mutableListOf()) { itemSeleccionado ->

            val intent = Intent(this, AddEditActivity::class.java)

            intent.putExtra("EXTRA_ITEM_EDITAR", itemSeleccionado)
            intent.putExtra("LAB_ID", laboratorioId)

            startActivity(intent)
        }

        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)
    }

    private fun configurarDeslizarParaBorrar() {

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                r: RecyclerView,
                v: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val posicion = viewHolder.bindingAdapterPosition
                val itemABorrar = adapter.listaAuditoria[posicion]

                viewModel.delete(itemABorrar)

                Toast.makeText(this@MainActivity, "Equipo Eliminado", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvAuditoria)
    }
}
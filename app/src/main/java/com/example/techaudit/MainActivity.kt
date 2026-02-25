package com.example.techaudit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techaudit.adapter.AuditAdapter
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.databinding.ActivityMainBinding
import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.AuditStatus
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: AuditAdapter

    private lateinit var database: AuditDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = (application as TechAuditApp).database

        setupRecyclerView()

        cargarDatosdeBaseDeDatos()

        binding.fabAgregar.setOnClickListener {

            val intent = Intent(this, AddEditActivity::class.java)
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
        //Inicializar el adapter pasando lista y la accion del clic

        adapter = AuditAdapter(mutableListOf()) { itemSeleccionado ->
            //Este lambda se ejecuta cuando doy clic a la tarjeta

            val intent = Intent(this, DetailActivity::class.java)

            intent.putExtra("EXTRA_ITEM", itemSeleccionado)

            startActivity(intent)

        }

        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)

        }

    private fun cargarDatosdeBaseDeDatos() {

        lifecycleScope.launch {
            val datos = database.auditDao().getAllItems()

            if (datos.isEmpty()) {
                Toast.makeText(this@MainActivity, "No hay datos", Toast.LENGTH_SHORT).show()
            }else {
                adapter.actualizarLista(datos)
            }
        }

    }

    private fun insertarRegistro(){

        val nuevoItem = AuditItem(
            id = UUID.randomUUID().toString(),
            nombre = "Equipo Nuevo #${(0..100).random()}",
            ubicacion = "Recepcion",
            fechaRegistro = Date().toString(),
            estado = AuditStatus.PENDIENTE,
            notas = "Registro Aleatorio"
        )

        lifecycleScope.launch {

            database.auditDao().insert(nuevoItem)

            Toast.makeText(this@MainActivity, "Registro Agregado", Toast.LENGTH_SHORT).show()

            cargarDatosdeBaseDeDatos()

        }
    }
}


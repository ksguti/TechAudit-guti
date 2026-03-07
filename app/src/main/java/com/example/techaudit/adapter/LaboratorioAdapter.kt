package com.example.techaudit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.databinding.ItemLaboratorioBinding
import com.example.techaudit.model.Laboratorio

class LaboratorioAdapter(
    private var listaLaboratorios: MutableList<Laboratorio>,
    private val onClick: (Laboratorio) -> Unit
) : RecyclerView.Adapter<LaboratorioAdapter.LaboratorioViewHolder>() {

    inner class LaboratorioViewHolder(
        private val binding: ItemLaboratorioBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lab: Laboratorio) {

            binding.tvNombreLaboratorio.text = lab.nombre
            binding.tvEdificio.text = lab.edificio

            // Mostrar ID del laboratorio en el label
            binding.tvEdificioLabel.text = "#${lab.id}"

            binding.root.setOnClickListener {
                onClick(lab)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaboratorioViewHolder {

        val binding = ItemLaboratorioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LaboratorioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaboratorioViewHolder, position: Int) {
        holder.bind(listaLaboratorios[position])

    }


    override fun getItemCount(): Int = listaLaboratorios.size

    fun actualizarLista(nuevaLista: List<Laboratorio>) {
        listaLaboratorios.clear()
        listaLaboratorios.addAll(nuevaLista)
        notifyDataSetChanged()
    }
    fun obtenerLaboratorio(position: Int): Laboratorio {
        return listaLaboratorios[position]
    }
}
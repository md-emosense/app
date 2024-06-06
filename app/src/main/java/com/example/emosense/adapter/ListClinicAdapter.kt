package com.example.emosense.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.databinding.ItemClinicBinding

class ListClinicAdapter : ListAdapter<ClinicItem, ListClinicAdapter.ViewHolder>(DIFF_CALLBACK) {
    private var listener: ((ClinicItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ClinicItem) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClinicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            listener?.invoke(user)
        }
    }

    class ViewHolder(private val binding: ItemClinicBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clinicName = binding.clinicName
        private val cityProvince = binding.cityprovince

        fun bind(clinic: ClinicItem) {
            clinicName.text = clinic.clinicName
            cityProvince.text = clinic.province + ", " + clinic.city

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ClinicItem>() {
            override fun areItemsTheSame(oldItem: ClinicItem, newItem: ClinicItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ClinicItem, newItem: ClinicItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
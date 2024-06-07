package com.example.emosense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.databinding.ItemClinicViewAllBinding

class ListClinicAllAdapter : ListAdapter<ClinicItem, ListClinicAllAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var listener: ((ClinicItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ClinicItem) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClinicViewAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clinic = getItem(position)
        holder.bind(clinic)
        holder.itemView.setOnClickListener {
            listener?.invoke(clinic)
        }
    }

    class ViewHolder(private val binding: ItemClinicViewAllBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clinicName = binding.clinicName
        private val cityProvince = binding.cityProvince
        private val suburbProvinceCity = binding.suburbProvinceCity
        private val clinicAddress = binding.clinicAddress
        private val clinicPhone = binding.clinicPhone
        private val expandButton = binding.expandButton
        private val showLessButton = binding.showLessButton
        private val detailsContainer = binding.detailsContainer

        fun bind(clinic: ClinicItem) {
            clinicName.text = clinic.clinicName
            cityProvince.text = "${clinic.suburb}, ${clinic.city}"
            clinicAddress.text = clinic.streetAddress
            suburbProvinceCity.text = "${clinic.suburb}, ${clinic.province}, ${clinic.city}"
            clinicPhone.text = clinic.phone

            expandButton.setOnClickListener {
                detailsContainer.visibility = View.VISIBLE
                expandButton.visibility = View.GONE
                showLessButton.visibility = View.VISIBLE
            }

            showLessButton.setOnClickListener {
                detailsContainer.visibility = View.GONE
                expandButton.visibility = View.VISIBLE
                showLessButton.visibility = View.GONE
            }
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

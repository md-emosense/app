package com.example.emosense.view.clinic

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListClinicAdapter
import com.example.emosense.adapter.ListClinicAllAdapter
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.databinding.ActivityClinicBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.main.MainViewModel

class ClinicActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityClinicBinding

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListClinicAllAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        layoutManager = LinearLayoutManager(this)
        binding.rvClinic.layoutManager = layoutManager

        adapter = ListClinicAllAdapter()
        binding.rvClinic.adapter = adapter

        viewModel.getAllClinic()

        viewModel.clinicResponse.observe(this) { clinic ->
            setClinicData(clinic)
        }

        setupView()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setClinicData(clinicData: List<ClinicItem>) {
        val adapter = ListClinicAllAdapter()
        adapter.submitList(clinicData)
        binding.rvClinic.adapter = adapter
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}
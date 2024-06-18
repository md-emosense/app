package com.example.emosense.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.databinding.ActivityOtherProfileBinding
import com.example.emosense.databinding.ActivityProfileBinding
import com.example.emosense.view.ViewModelFactory

class OtherProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityOtherProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
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

    private fun setupAction(){
        val id = intent.getIntExtra("extra_id",-1)

        setupProfile(id)

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupProfile(id: Int) {
        viewModel.getProfile(id)

        viewModel.profileResponse.observe(this){ user ->
            binding.tvFullName.text = user.fullName
            binding.familyNameEditText.setText(user.childName)
            binding.familyDobEditText.setText(user.childBirthday)
            binding.familyAdhdDescriptionEditText.setText(user.adhdDesc)
        }
    }


    companion object{
        const val EXTRA_ID = "extra_id"
    }
}
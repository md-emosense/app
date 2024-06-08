package com.example.emosense.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.databinding.ActivityLoginBinding
import com.example.emosense.databinding.ActivityMainBinding
import com.example.emosense.databinding.ActivityProfileBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.login.LoginActivity
import com.example.emosense.view.main.MainViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}
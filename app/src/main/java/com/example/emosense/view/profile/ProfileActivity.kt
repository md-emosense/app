package com.example.emosense.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        // Observasi session untuk memeriksa login
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                setupProfile(user.id)
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun setupProfile(userId: Int) {
        viewModel.getProfile(userId)

        viewModel.profileResponse.observe(this) { profile ->
            profile?.let {
                binding.tvFullName.text = it.fullName
                binding.tvEmail.text = it.email
                binding.tvChildName.text = it.childName
                binding.tvChildBirthday.text = it.childBirthday
                binding.tvAdhdDesc.text = it.adhdDesc
            }
        }
    }
}

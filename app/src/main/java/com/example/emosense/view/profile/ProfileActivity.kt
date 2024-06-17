package com.example.emosense.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.emosense.R
import com.example.emosense.databinding.ActivityProfileBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.about.AboutActivity
import com.example.emosense.view.login.LoginActivity
import com.example.emosense.view.main.MainViewModel
import com.example.emosense.view.news.NewsDetailActivity

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
            } else {
                setupProfile(user.id)
            }
        }

        binding.tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    viewModel.logout()
                }
                .setNegativeButton("Tidak") { _, _ ->
                }
                .show()
        }

        binding.tvAbout.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        setupView()
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

    private fun setupProfile(userId: Int) {
        viewModel.getProfile(userId)

        viewModel.profileResponse.observe(this) { profile ->
            profile?.let { profile ->
                binding.tvFullName.text = profile.fullName
                binding.tvEmail.text = profile.email
//                binding.tvChildName.text = it.childName
//                binding.tvChildBirthday.text = it.childBirthday
//                binding.tvAdhdDesc.text = it.adhdDesc

                val coloredEmail = "<font color='${ContextCompat.getColor(this, R.color.primary)}'>${profile.email}</font>"

                binding.tvChildData.setOnClickListener {
                    val intent = Intent(this, ChildDataActivity::class.java)
                    intent.putExtra(ChildDataActivity.EXTRA_USER, profile)
                    startActivity(intent)
                }

                binding.tvEditProfile.setOnClickListener {
                    val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
                    intent.putExtra("profile", profile)
                    startActivity(intent)
                }

                binding.tvChangePassword.setOnClickListener {
                    val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
                    intent.putExtra("profile", profile)
                    startActivity(intent)
                }
                binding.tvCall.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("Hubungi Kami")
                        .setMessage(Html.fromHtml("Kami akan mengirimkan email kepada $coloredEmail untuk kontak lebih lanjut. Lanjutkan?"))
                        .setPositiveButton("Ya") { _, _ ->
                            AlertDialog.Builder(this)
                                .setMessage("Maaf, kontak kami belum tersedia.")
                                .setPositiveButton("OK") { _, _ -> }
                                .show()
                        }
                        .setNegativeButton("Tidak") { _, _ ->
                        }
                        .show()
                }
            }
        }
    }
}

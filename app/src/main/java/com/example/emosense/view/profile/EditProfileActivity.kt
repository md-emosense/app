package com.example.emosense.view.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.data.response.UserData
import com.example.emosense.databinding.ActivityChangeChildDataBinding
import com.example.emosense.databinding.ActivityEditProfileBinding
import com.example.emosense.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel by viewModels<UpdateDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER, UserData::class.java)
        }else{
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER)
        }

        val password: String? = intent.getStringExtra(ChangeChildDataActivity.EXTRA_PASS)

        setupView()
        setupAction(user!!,password!!)
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
    private fun setupAction(user: UserData, password: String) {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = inputFormat.parse(user.childBirthday!!)
        val formattedDate = parsedDate?.let { outputFormat.format(it) } ?: user.childBirthday

        binding.nameEditText.setText(user.fullName)
        binding.emailEditText.setText(user.email)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.changeButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val childBirthday = formattedDate

            viewModel.getSession().observe(this) { userId ->
                viewModel.editProfile(user, password, userId.id, name, childBirthday, email)
            }

            viewModel.message.observe(this){message ->
                message?.let {
                    if (it == "Data berhasil diubah") {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil")
                            setMessage(message)
                            setPositiveButton("OK") { _, _ ->
                                finish()
                                val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
                                startActivity(intent)
                            }
                            viewModel.resetMessage()
                            create()
                            show()
                        }
                    } else if (it == "Email sudah pernah digunakan") {
                        AlertDialog.Builder(this).apply {
                            setTitle("Error")
                            setMessage(it)
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            viewModel.resetMessage()
                            create()
                            show()
                        }
                    }
                }

            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_PASS = "extra_pass"
    }
}

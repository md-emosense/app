package com.example.emosense.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.emosense.R
import com.example.emosense.data.response.UserData
import com.example.emosense.databinding.ActivityChangePasswordBinding
import com.example.emosense.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class ChangePasswordActivity : AppCompatActivity() {

    private val viewModel by viewModels<UpdateDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER, UserData::class.java)
        } else {
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER)
        }

        setupView()
        if (user != null) setupAction(user)
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

    private fun setupAction(user: UserData) {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = inputFormat.parse(user.childBirthday!!)
        val formattedDate = parsedDate?.let { outputFormat.format(it) } ?: user.childBirthday

        binding.changeButton.setOnClickListener {
            val oldPassword = binding.oldPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            val confirmNewPassword = binding.confirmPasswordEditText.text.toString()
            val childBirthday = formattedDate

            when {
                oldPassword == newPassword -> {
                    showAlertDialog("Peringatan", "Password lama dan password baru tidak boleh sama.")
                }
                newPassword != confirmNewPassword -> {
                    showAlertDialog("Peringatan", "Password baru dan konfirmasi password tidak cocok.")
                }
                else -> {
                    viewModel.checkPassword(user.email.toString(), oldPassword)
                    viewModel.message.observe(this) { message ->
                        if (message == "Success") {
                            viewModel.getSession().observe(this) { userId ->
                                viewModel.changePassword(user, newPassword, userId.id, childBirthday)
                            }
                            viewModel.message.observe(this) { updateMessage ->
                                if (updateMessage == "Data berhasil diubah") {
                                    AlertDialog.Builder(this).apply {
                                        setTitle("Berhasil")
                                        setMessage(updateMessage)
                                        setPositiveButton("OK") { _, _ ->
                                            finish()
                                            val intent = Intent(this@ChangePasswordActivity, ProfileActivity::class.java)
                                            startActivity(intent)
                                        }
                                        create()
                                        show()
                                    }
                                } else {
                                    showAlertDialog("Error", updateMessage)
                                }
                            }
                        } else {
                            showAlertDialog("Error", message)
                        }
                    }
                }
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}

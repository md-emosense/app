package com.example.emosense.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.emosense.databinding.ActivitySignUpBinding
import com.example.emosense.view.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signupViewModel:  SignupViewModel by lazy {
        ViewModelProvider(this)[SignupViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()
        validateInput()
        setupAction()

        signupViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        signupViewModel.message.observe(this) { message ->
            message?.let {
                if (it == "Akun berhasil dibuat") {
                    AlertDialog.Builder(this).apply {
                        setTitle("Berhasil")
                        setMessage(message)
                        setPositiveButton("Masuk") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage(it)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            }
        }

        submitCheck()

        setupDatePicker()
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.familyDobEditText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        binding.familyDobEditText.setOnClickListener {
            datePickerDialog.show()
        }

        binding.familyDobEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerDialog.show()
            }
        }
    }

    private fun setupAction() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun submitCheck() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val childName = binding.familyNameEditText.text.toString()
            val adhdDesc = binding.familyAdhdDescriptionEditText.text.toString()
            val birthday = binding.familyDobEditText.text.toString()

            var isValid = true

            if (name.isEmpty()) {
                binding.nameEditTextLayout.error = "Nama tidak boleh kosong"
                isValid = false
            } else {
                binding.nameEditTextLayout.error = null
            }

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Email tidak boleh kosong"
                isValid = false
            } else {
                binding.emailEditTextLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = "Password tidak boleh kosong"
                isValid = false
            } else {
                binding.passwordEditTextLayout.error = null
            }

            if (childName.isEmpty()) {
                binding.familyNameEditTextLayout.error = "Nama keluarga tidak boleh kosong"
                isValid = false
            } else {
                binding.familyNameEditTextLayout.error = null
            }

            if (adhdDesc.isEmpty()) {
                binding.familyAdhdDescriptionEditTextLayout.error = "Deskripsi tidak boleh kosong"
                isValid = false
            } else {
                binding.familyAdhdDescriptionEditTextLayout.error = null
            }

            if (birthday.isEmpty()) {
                binding.familyDobEditTextLayout.error = "Tanggal lahir keluarga tidak boleh kosong"
                isValid = false
            } else {
                if (!isDateValid(birthday)) {
                    binding.familyDobEditTextLayout.error = "Tanggal lahir harus dalam format yyyy-MM-dd"
                    isValid = false
                } else {
                    binding.familyDobEditTextLayout.error = null
                }
            }

            if (isValid) {
                signupViewModel.signup(name, email, password, childName, adhdDesc, birthday)
            }
        }
    }

    private fun isDateValid(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun validateInput() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    binding.passwordEditTextLayout.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    binding.passwordEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.contains("@")) {
                    binding.emailEditTextLayout.error = "Email harus mengandung @"
                } else {
                    binding.emailEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
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

    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
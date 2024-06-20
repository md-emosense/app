package com.example.emosense.view.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.emosense.R
import com.example.emosense.data.response.UserData
import com.example.emosense.databinding.ActivityChangeChildDataBinding
import com.example.emosense.databinding.ActivitySignUpBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.flashcards.FlashcardsViewModel
import com.example.emosense.view.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChangeChildDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeChildDataBinding

    private val viewModel by viewModels<UpdateDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeChildDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER, UserData::class.java)
        }else{
            intent.getParcelableExtra<UserData>(ChangeChildDataActivity.EXTRA_USER)
        }

        val password: String? = intent.getStringExtra(ChangeChildDataActivity.EXTRA_PASS)

        setupAction(user!!,password!!)
        setupDatePicker()
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

    private fun setupAction(user: UserData, password: String) {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = inputFormat.parse(user.childBirthday!!)
        val formattedDate = parsedDate?.let { outputFormat.format(it) } ?: user.childBirthday

        binding.nameEditText.setText(user.childName)
        binding.familyDobEditText.setText(formattedDate)
        binding.familyAdhdDescriptionEditText.setText(user.adhdDesc)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.changeButton.setOnClickListener {
            val childName = binding.nameEditText.text.toString()
            val childBirthday = binding.familyDobEditText.text.toString()
            val childAdhd = binding.familyAdhdDescriptionEditText.text.toString()

            viewModel.getSession().observe(this) { userId ->
                viewModel.updateChildData(user, password, userId.id, childName, childBirthday, childAdhd)
            }

            viewModel.message.observe(this){message ->
                message?.let {
                    if (it == "Data berhasil diubah") {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil")
                            setMessage(message)
                            setPositiveButton("OK") { _, _ ->
                                val intent = Intent(this@ChangeChildDataActivity, ProfileActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                startActivity(intent)
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
        }
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

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_PASS = "extra_pass"
    }
}
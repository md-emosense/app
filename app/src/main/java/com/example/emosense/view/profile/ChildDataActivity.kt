package com.example.emosense.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.data.dataclass.News
import com.example.emosense.data.response.UserData
import com.example.emosense.databinding.ActivityChildDataBinding
import com.example.emosense.databinding.ActivityMainBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.login.LoginViewModel
import com.example.emosense.view.main.HomeFragment
import com.example.emosense.view.main.MainActivity
import com.example.emosense.view.news.NewsDetailActivity
import com.example.emosense.view.predict.PredictActivity

class ChildDataActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityChildDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        val user = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<UserData>(ChildDataActivity.EXTRA_USER, UserData::class.java)
        }else{
            intent.getParcelableExtra<UserData>(ChildDataActivity.EXTRA_USER)
        }

        val id = intent.getIntExtra("extra_id", -1)

        if (user != null) setupAction(user,id)
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

    private fun setupAction(user: UserData, id: Int) {
        binding.tvChildName.text = user.childName
        binding.tvChildBirthday.text = user.childBirthday
        binding.tvAdhdDesc.text = user.adhdDesc

        binding.changeDataButton.setOnClickListener {
            checkPassword(user, id)
        }
    }

    private fun checkPassword(user: UserData, id: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_enter_password, null)
        val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Konfirmasi password")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val password = etPassword.text.toString()
                if (password.isNotEmpty()) {
                    viewModel.login(user.email.toString(), password)
                    viewModel.message.observe(this){
                        viewModel.message.observe(this) { message ->
                            message?.let {
                                if (it == "Success") {
                                    val intent = Intent(this, ChangeChildDataActivity::class.java)
                                    intent.putExtra(ChangeChildDataActivity.EXTRA_USER, user)
                                    intent.putExtra(ChangeChildDataActivity.EXTRA_PASS, password)
                                    startActivity(intent)
                                    finish()
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
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}
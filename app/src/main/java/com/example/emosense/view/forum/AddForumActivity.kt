package com.example.emosense.view.forum

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.emosense.databinding.ActivityAddForumBinding
import com.example.emosense.view.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddForumActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddForumViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAddForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddForumBinding.inflate(layoutInflater)
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

    private fun setupAction() {
        binding.addButton.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val body = binding.editBody.text.toString()

            when {
                title.isEmpty() -> showInputErrorDialog("Judul tidak boleh kosong.")
                body.isEmpty() -> showInputErrorDialog("Isi tidak boleh kosong.")
                else -> {
                    Toast.makeText(this, "Forum terkirim!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddForumActivity, ForumActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showInputErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Input Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

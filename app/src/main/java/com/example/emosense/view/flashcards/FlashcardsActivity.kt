package com.example.emosense.view.flashcards

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListFlashcardAdapter
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.data.response.SpeechItem
import com.example.emosense.databinding.ActivityFlashcardsBinding
import com.example.emosense.view.ViewModelFactory
import okio.IOException


class FlashcardsActivity : AppCompatActivity() {
    private val viewModel by viewModels<FlashcardsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityFlashcardsBinding

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListFlashcardAdapter

    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupFlashcards()

        binding.backButton.setOnClickListener {
            finish()
        }

        setupView()
    }

    private fun setupFlashcards() {
        layoutManager = LinearLayoutManager(this)
        binding.rvFlashcard.layoutManager = layoutManager

        adapter = ListFlashcardAdapter()
        binding.rvFlashcard.adapter = adapter

        viewModel.getAllSpeech()

        viewModel.speechResponse.observe(this) { clinic ->
            setSpeechData(clinic)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setSpeechData(speechItem: List<SpeechItem>) {
        adapter.submitList(speechItem)
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

}
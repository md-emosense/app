package com.example.emosense.view.flashcards

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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

//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupAction() {
//        binding.btnFlip.setOnClickListener {
//            flipCard(binding.card1, R.id.cardFront1, R.id.cardBack1)
//        }
//
//        binding.btnFlip2.setOnClickListener {
//            flipCard(binding.card2, R.id.cardFront2, R.id.cardBack2)
//        }
//
//        binding.btnFlip3.setOnClickListener {
//            flipCard(binding.card3, R.id.cardFront3, R.id.cardBack3)
//        }
//
//        binding.playSenang.setOnClickListener {
//            playAudio("https://storage.googleapis.com/emosense-bucket/audio-mp3/senang.mp3", "senang")
//        }
//
//        binding.playSedih.setOnClickListener {
//            playAudio("https://storage.googleapis.com/emosense-bucket/audio-mp3/sedih.mp3", "sedih")
//        }
//
//        binding.playMarah.setOnClickListener {
//            playAudio("https://storage.googleapis.com/emosense-bucket/audio-mp3/marah.mp3", "marah")
//        }
//
//        binding.backButton.setOnClickListener {
//            finish()
//        }
//
//        binding.btnFlip.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (binding.card2.tag == "back") {
//                    flipCard(binding.card2, R.id.cardFront2, R.id.cardBack2)
//                }
//                if (binding.card3.tag == "back") {
//                    flipCard(binding.card3, R.id.cardFront3, R.id.cardBack3)
//                }
//            }
//            false
//        }
//
//        binding.btnFlip2.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (binding.card1.tag == "back") {
//                    flipCard(binding.card1, R.id.cardFront1, R.id.cardBack1)
//                }
//                if (binding.card3.tag == "back") {
//                    flipCard(binding.card3, R.id.cardFront3, R.id.cardBack3)
//                }
//            }
//            false
//        }
//
//        binding.btnFlip3.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (binding.card1.tag == "back") {
//                    flipCard(binding.card1, R.id.cardFront1, R.id.cardBack1)
//                }
//                if (binding.card2.tag == "back") {
//                    flipCard(binding.card2, R.id.cardFront2, R.id.cardBack2)
//                }
//            }
//            false
//        }
//    }

//    private fun playAudio(audioUrl: String, type: String) {
//        mediaPlayer = MediaPlayer()
//        mediaPlayer!!.setAudioAttributes(
//            AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .build()
//        );
//        try {
//            mediaPlayer!!.setDataSource(audioUrl)
//            mediaPlayer!!.prepare()
//            mediaPlayer!!.start()
//
//            Toast.makeText(this, "Kamu sedang mendengar audio $type", Toast.LENGTH_LONG).show()
//        } catch (e : IOException) {
//            e.printStackTrace()
//        }
//
//    }

//    private fun flipCard(card: View, frontId: Int, backId: Int) {
//        val backVisibility = if (card.tag == "front") View.VISIBLE else View.GONE
//        val frontVisibility = if (card.tag == "back") View.VISIBLE else View.GONE
//
//        val flipInAnimator = if (card.tag == "front") R.animator.flip_to_back else R.animator.flip_to_front
//        val flipOutAnimator = if (card.tag == "back") R.animator.flip_to_front else R.animator.flip_to_back
//
//        val animFlipIn = AnimatorInflater.loadAnimator(this, flipInAnimator)
//        animFlipIn.setTarget(card)
//
//        animFlipIn.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                card.findViewById<View>(frontId).visibility = frontVisibility
//                card.findViewById<View>(backId).visibility = backVisibility
//
//                card.tag = if (card.tag == "front") "back" else "front"
//            }
//        })
//
//        val animFlipOut = AnimatorInflater.loadAnimator(this, flipOutAnimator)
//        animFlipOut.setTarget(card)
//
//        animFlipIn.start()
//        animFlipOut.start()
//    }

}
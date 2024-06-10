package com.example.emosense.view.flashcards

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.emosense.R

import com.example.emosense.databinding.ActivityFlashcardsBinding

class FlashcardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.btnFlip.setOnClickListener {
            flipCard(binding.card1, R.id.cardFront1, R.id.cardBack1)
        }

        binding.btnFlip2.setOnClickListener {
            flipCard(binding.card2, R.id.cardFront2, R.id.cardBack2)
        }

        binding.btnFlip3.setOnClickListener {
            flipCard(binding.card3, R.id.cardFront3, R.id.cardBack3)
        }
    }

    private fun flipCard(card: View, frontId: Int, backId: Int) {
        val backVisibility = if (card.tag == "front") View.VISIBLE else View.GONE
        val frontVisibility = if (card.tag == "back") View.VISIBLE else View.GONE

        val flipInAnimator = if (card.tag == "front") R.animator.flip_to_back else R.animator.flip_to_front
        val flipOutAnimator = if (card.tag == "back") R.animator.flip_to_front else R.animator.flip_to_back

        val animFlipIn = AnimatorInflater.loadAnimator(this, flipInAnimator)
        animFlipIn.setTarget(card)

        animFlipIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                card.findViewById<View>(frontId).visibility = frontVisibility
                card.findViewById<View>(backId).visibility = backVisibility

                card.tag = if (card.tag == "front") "back" else "front"
            }
        })

        val animFlipOut = AnimatorInflater.loadAnimator(this, flipOutAnimator)
        animFlipOut.setTarget(card)

        animFlipIn.start()
        animFlipOut.start()
    }

}
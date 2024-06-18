package com.example.emosense.view.predict

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.emosense.R
import com.example.emosense.data.response.PredictResponse
import com.example.emosense.databinding.ActivityResultBinding
import com.example.emosense.view.main.MainActivity

import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageUriString = intent.getStringExtra("extra_image")
        val predictResponseJson = intent.getStringExtra("extra_response")
        val predictResponse = Gson().fromJson(predictResponseJson, PredictResponse::class.java)

        imageUriString?.let {
            val imageUri = Uri.parse(it)
            Glide.with(this).load(imageUri).into(binding.previewImageView)
        }

        predictResponse?.let {
            val label = it.prediction

            val translatedLabel = when (label) {
                "angry" -> "Marah"
                "disgust" -> "Jijik"
                "fear" -> "Takut"
                "happy" -> "Senang"
                "neutral" -> "Netral"
                "sad" -> "Sedih"
                "surprise" -> "Terkejut"
                else -> "Tidak terdeteksi"
            }
            binding.cameraButton.text = translatedLabel

            val drawableResId = when (label) {
                "angry" -> R.drawable.angry_icon
                "disgust" -> R.drawable.disgust_icon
                "fear" -> R.drawable.scared_icon
                "happy" -> R.drawable.happy_icon
                "neutral" -> R.drawable.neutral
                "sad" -> R.drawable.sad_icon
                "surprise" -> R.drawable.surprised_icon
                else -> R.drawable.happy_icon
            }

            val drawable = ContextCompat.getDrawable(this, drawableResId)
            drawable?.let {
                binding.cameraButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            }

        }


        binding.backButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_RESPONSE = "extra_response"
    }
}

package com.example.emosense.view.news

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.data.dataclass.News
import com.example.emosense.databinding.ActivityNewsBinding
import com.example.emosense.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailPhoto: ImageView = findViewById(R.id.ivNews)
        val detailTitle: TextView = findViewById(R.id.tvTitle)
        val detailDesc: TextView = findViewById(R.id.tvDesc)

        val news = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<News>(EXTRA_NEWS, News::class.java)
        }else{
            intent.getParcelableExtra<News>(EXTRA_NEWS)
        }

        if (news != null){
            detailTitle.text = news.title
            detailDesc.text = news.description
            detailPhoto.setImageResource(news.photo)
        }

        setupView()

        binding.backButton.setOnClickListener {
            finish()
        }
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
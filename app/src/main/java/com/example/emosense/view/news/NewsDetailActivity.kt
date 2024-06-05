package com.example.emosense.view.news

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emosense.R
import com.example.emosense.data.dataclass.News

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

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
    }
}
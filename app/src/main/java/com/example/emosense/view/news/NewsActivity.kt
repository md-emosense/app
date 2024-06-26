package com.example.emosense.view.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.R
import com.example.emosense.adapter.ListNewsAdapter
import com.example.emosense.data.dataclass.News
import com.example.emosense.databinding.ActivityNewsBinding
import com.example.emosense.databinding.ActivityPredictBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var rvNews: RecyclerView
    private val list = ArrayList<News>()

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        rvNews = findViewById(R.id.rvNews)
        rvNews.setHasFixedSize(true)

        list.addAll(getNewsList())
        showRecyclerList()

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

    @SuppressLint("Recycle")
    private fun getNewsList(): ArrayList<News>{
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listNews = ArrayList<News>()
        for(i in dataTitle.indices){
            val news = News(
                dataTitle[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1)
            )
            listNews.add(news)
        }
        return listNews
    }

    private fun showRecyclerList(){
        rvNews.layoutManager = LinearLayoutManager(this)
        val listNewsAdapter = ListNewsAdapter(list)
        rvNews.adapter = listNewsAdapter

        listNewsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                showSelectedNews(data)
            }
        })
    }

    private fun showSelectedNews(news: News) {
        val moveToDetail = Intent(this@NewsActivity, NewsDetailActivity::class.java)
        moveToDetail.putExtra(NewsDetailActivity.EXTRA_NEWS,news)
        startActivity(moveToDetail)
    }

}
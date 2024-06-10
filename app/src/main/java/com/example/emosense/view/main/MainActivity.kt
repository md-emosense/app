package com.example.emosense.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.R
import com.example.emosense.adapter.ListClinicAdapter
import com.example.emosense.data.dataclass.News
import com.example.emosense.databinding.ActivityMainBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.clinic.ClinicActivity
import com.example.emosense.view.login.LoginActivity
import com.example.emosense.adapter.ListNewsAdapter
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.view.news.NewsActivity
import com.example.emosense.view.news.NewsDetailActivity
import com.example.emosense.view.predict.PredictActivity
import com.example.emosense.view.profile.ProfileActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvNews: RecyclerView
    private val list = ArrayList<News>()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListClinicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClinic()
        setupView()
        setupAction()
        setNews()
    }

    private fun setupClinic() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvClinic.layoutManager = layoutManager

        adapter = ListClinicAdapter()
        binding.rvClinic.adapter = adapter

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.getAllClinic()

        viewModel.clinicResponse.observe(this) { clinic ->
            setClinicData(clinic)
        }
    }

    private fun setClinicData(clinicData: List<ClinicItem>) {
        val topFiveClinics = if (clinicData.size > 5) clinicData.subList(0, 5) else clinicData
        adapter.submitList(topFiveClinics)
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
        binding.profileButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.tvMoreNews.setOnClickListener {
            val intent = Intent(this@MainActivity, NewsActivity::class.java)
            startActivity(intent)
        }

        binding.tvMoreClinic.setOnClickListener {
            val intent = Intent(this@MainActivity, ClinicActivity::class.java)
            startActivity(intent)
        }

        binding.predictButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PredictActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setNews(){
        rvNews = binding.rvNews
        rvNews.setHasFixedSize(true)

        list.addAll(getNewsList())
        showRecyclerList()
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
            if (i == 2) break
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
        val moveToDetail = Intent(this@MainActivity, NewsDetailActivity::class.java)
        moveToDetail.putExtra(NewsDetailActivity.EXTRA_NEWS,news)
        startActivity(moveToDetail)
    }
}
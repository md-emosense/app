package com.example.emosense.view.forum

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListClinicAdapter
import com.example.emosense.adapter.ListForumAdapter
import com.example.emosense.adapter.ListNewsAdapter
import com.example.emosense.data.dataclass.News
import com.example.emosense.data.response.ForumItem
import com.example.emosense.databinding.ActivityForumBinding
import com.example.emosense.databinding.ActivityMainBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.clinic.ClinicActivity
import com.example.emosense.view.main.MainActivity
import com.example.emosense.view.main.MainViewModel
import com.example.emosense.view.news.NewsActivity
import com.example.emosense.view.news.NewsDetailActivity
import com.example.emosense.view.predict.PredictActivity
import com.example.emosense.view.profile.ProfileActivity

class ForumActivity : AppCompatActivity() {

    private val viewModel by viewModels<ForumViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityForumBinding
    private lateinit var forumAdapter: ListForumAdapter

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        viewModel.getForum()
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
        binding.addButton.setOnClickListener{
            val intent = Intent(this@ForumActivity, AddForumActivity::class.java)
            startActivity(intent)
        }

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvForum.layoutManager = layoutManager

        forumAdapter = ListForumAdapter()
        binding.rvForum.adapter = forumAdapter

        viewModel.getForum()

        viewModel.forumResponse.observe(this) { forum ->
            setForum(forum)
        }

        forumAdapter.setOnItemClickCallback(object : ListForumAdapter.OnItemClickCallback{
            override fun onItemClicked(id: Int){
                showSelectedForum(id)
            }
        })
    }

    private fun setForum(forum: List<ForumItem?>?){
        forumAdapter.submitList(forum)
    }

    private fun showSelectedForum(id: Int) {
        val moveToDetail = Intent(this@ForumActivity, DetailForumActivity::class.java)
        moveToDetail.putExtra(DetailForumActivity.EXTRA_ID,id)
        startActivity(moveToDetail)
    }


}
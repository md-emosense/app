package com.example.emosense.view.forum

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListForumAdapter
import com.example.emosense.adapter.ListReplyAdapter
import com.example.emosense.data.response.ForumItem
import com.example.emosense.data.response.Replies
import com.example.emosense.databinding.ActivityDetailForumBinding
import com.example.emosense.databinding.ActivityForumBinding
import com.example.emosense.utils.formatCreatedAt
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.profile.ChildDataActivity
import com.example.emosense.view.profile.OtherProfileActivity
import com.example.emosense.view.profile.ProfileActivity

class DetailForumActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailForumViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailForumBinding
    private lateinit var adapter: ListReplyAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var numOfReplies: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
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

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction() {

        val forumId = intent.getIntExtra(EXTRA_ID, -1)
        viewModel.getForumDetail(forumId)
        Log.d("DetailForumActivity", "Fetching forum detail for ID: $forumId")

        viewModel.forumResponse.observe(this) { forum ->
            if (forum != null) {
                Log.d("DetailForumActivity", "Forum response observed: $forum")
                binding.tvTitle.text = forum.forum?.judul
                binding.tvDesc.text = forum.forum?.isi
                binding.tvName.text = forum.forum?.userName
                binding.tvTime.text = formatCreatedAt(forum.forum?.createdAt ?: "")

                setReplies(forum.replies)
                numOfReplies = forum.replies?.size ?: 0
                binding.tvNumofComments.text = "($numOfReplies)"

                val profileClickListener = View.OnClickListener {
                    viewModel.getSession().observe(this){user ->
                        if (user.id == forum.forum?.userId){
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                        }else{
                            val intent = Intent(this, OtherProfileActivity::class.java)
                            intent.putExtra(OtherProfileActivity.EXTRA_ID, forum.forum?.userId!!)
                            startActivity(intent)
                        }
                    }

                }

                binding.tvName.setOnClickListener(profileClickListener)
                binding.tvTime.setOnClickListener(profileClickListener)
                binding.ivProfile.setOnClickListener(profileClickListener)

            } else {
                Log.e("DetailForumActivity", "Forum data is null")
            }
        }

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvReplies.layoutManager = layoutManager

        adapter = ListReplyAdapter()
        binding.rvReplies.adapter = adapter

        binding.sendButton.setOnClickListener{
            val reply = binding.etReply.text.toString()

            viewModel.getSession().observe(this) { user ->
                viewModel.postReply(forumId, user.id, reply) { newReply ->
                    val replyWithUserName = newReply.copy(userName = user.name)
                    val updatedReplies = adapter.currentList.toMutableList()
                    updatedReplies.add(replyWithUserName)
                    adapter.submitList(updatedReplies)
                    binding.etReply.text.clear()
                    binding.rvReplies.scrollToPosition(updatedReplies.size - 1)
                }
            }
        }
    }

    private fun setReplies(replies: List<Replies?>?) {
        Log.d("DetailForumActivity", "Setting replies: $replies")
        adapter.submitList(replies)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}

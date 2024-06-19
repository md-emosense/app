package com.example.emosense.view.forum

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListForumAdapter
import com.example.emosense.data.response.ForumItem
import com.example.emosense.databinding.FragmentForumBinding
import com.example.emosense.utils.formatCreatedAt
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.profile.OtherProfileActivity
import com.example.emosense.view.profile.ProfileActivity

class ForumFragment : Fragment() {

    private val viewModel by viewModels<ForumViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel2 by viewModels<DetailForumViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!

    private lateinit var forumAdapter: ListForumAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()

        viewModel.forumResponse.observe(viewLifecycleOwner) { forum ->
            setForum(forum)
        }

        viewModel.getForum()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setupAction() {
        binding.addButton.setOnClickListener{
            val intent = Intent(requireContext(), AddForumActivity::class.java)
            startActivity(intent)
        }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvForum.layoutManager = layoutManager

        forumAdapter = ListForumAdapter()
        binding.rvForum.adapter = forumAdapter

        forumAdapter.setOnItemClickCallback(object : ListForumAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                showSelectedForum(id)
            }
        })
    }

    private fun setForum(forum: List<ForumItem?>?) {
        val reversedForum = forum?.mapNotNull { it?.copy() }?.reversed() 

        reversedForum?.forEachIndexed { index, forumItem ->
            forumItem?.id?.let { forumId ->
                viewModel2.getForumDetail(forumId)
                observeForumDetail(index, forumItem)
            }
        }

        reversedForum?.let {
            forumAdapter.submitList(it)
        }
    }

    private fun observeForumDetail(index: Int, forumItem: ForumItem?) {
        viewModel2.forumResponse.observe(viewLifecycleOwner) { forumDetail ->
            forumDetail?.let {
                val forumId = forumItem?.id ?: return@observe
                if (forumDetail.forum?.id == forumId) {
                    forumItem.numOfReplies = forumDetail.replies?.size ?: 0
                    forumAdapter.notifyItemChanged(index)
                }
            }
        }
    }

    private fun showSelectedForum(id: Int) {
        val moveToDetail = Intent(requireContext(), DetailForumActivity::class.java)
        moveToDetail.putExtra(DetailForumActivity.EXTRA_ID, id)
        startActivity(moveToDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
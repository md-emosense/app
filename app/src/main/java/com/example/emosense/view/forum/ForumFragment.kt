package com.example.emosense.view.forum

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emosense.R
import com.example.emosense.adapter.ListForumAdapter
import com.example.emosense.data.response.ForumItem
import com.example.emosense.databinding.FragmentForumBinding
import com.example.emosense.view.ViewModelFactory

class ForumFragment : Fragment() {

    private val viewModel by viewModels<ForumViewModel> {
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

        viewModel.getForum()
    }

    private fun setupAction() {
        binding.addButton.setOnClickListener{
            val intent = Intent(requireContext(), AddForumActivity::class.java)
            //temporary
            startActivity(intent)
        }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvForum.layoutManager = layoutManager

        forumAdapter = ListForumAdapter()
        binding.rvForum.adapter = forumAdapter

        viewModel.getForum()

        viewModel.forumResponse.observe(viewLifecycleOwner) { forum ->
            setForum(forum)
        }

        forumAdapter.setOnItemClickCallback(object : ListForumAdapter.OnItemClickCallback{
            override fun onItemClicked(id: Int){
                showSelectedForum(id)
            }
        })
    }

    private fun setForum(forum: List<ForumItem?>?){
        val reversedForum = forum?.reversed() ?: return
        forumAdapter.submitList(reversedForum)
    }

    private fun showSelectedForum(id: Int) {
        val moveToDetail = Intent(requireContext(), DetailForumActivity::class.java)
        moveToDetail.putExtra(DetailForumActivity.EXTRA_ID,id)
        startActivity(moveToDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

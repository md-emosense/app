package com.example.emosense.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.R
import com.example.emosense.adapter.ListClinicAdapter
import com.example.emosense.adapter.ListNewsAdapter
import com.example.emosense.data.dataclass.News
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.databinding.FragmentHomeBinding
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.clinic.ClinicActivity
import com.example.emosense.view.login.LoginActivity
import com.example.emosense.view.news.NewsActivity
import com.example.emosense.view.news.NewsDetailActivity
import com.example.emosense.view.predict.PredictActivity
import com.example.emosense.view.flashcards.FlashcardsActivity
import com.example.emosense.view.profile.ChangePasswordActivity
import com.example.emosense.view.profile.ChildDataActivity
import com.example.emosense.view.profile.EditProfileActivity
import com.example.emosense.view.profile.ProfileActivity
import com.example.emosense.view.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel2 by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvNews: RecyclerView
    private val list = ArrayList<News>()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListClinicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClinic()
        setupUser()
        setupAction()
        setNews()
    }

    private fun setupUser() {
        viewModel2.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                setupProfile(user.id)
            }
        }
    }

    private fun setupProfile(userId: Int) {
        viewModel2.getProfile(userId)

        viewModel2.profileResponse.observe(viewLifecycleOwner) { profile ->
            profile?.let { profile ->
                binding.tvName.text = profile.fullName
            }
        }
    }

    private fun setupClinic() {
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvClinic.layoutManager = layoutManager

        adapter = ListClinicAdapter()
        binding.rvClinic.adapter = adapter

        viewModel.getAllClinic()

        viewModel.clinicResponse.observe(viewLifecycleOwner) { clinic ->
            setClinicData(clinic)
        }
    }

    private fun setClinicData(clinicData: List<ClinicItem>) {
        val topFiveClinics = if (clinicData.size > 5) clinicData.subList(0, 5) else clinicData
        adapter.submitList(topFiveClinics)
    }

    private fun setupAction() {

        binding.tvMoreNews.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivity::class.java)
            startActivity(intent)
        }

        binding.tvMoreClinic.setOnClickListener {
            val intent = Intent(requireContext(), ClinicActivity::class.java)
            startActivity(intent)
        }

        binding.imageView.setOnClickListener {
            val intent = Intent(requireContext(), FlashcardsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setNews() {
        rvNews = binding.rvNews
        rvNews.setHasFixedSize(true)

        list.addAll(getNewsList())
        showRecyclerList()
    }

    @SuppressLint("Recycle")
    private fun getNewsList(): ArrayList<News> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listNews = ArrayList<News>()
        for (i in dataTitle.indices) {
            val news = News(
                dataTitle[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1)
            )
            listNews.add(news)
            if (i == 3) break
        }
        return listNews
    }

    private fun showRecyclerList() {
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        val listNewsAdapter = ListNewsAdapter(list)
        rvNews.adapter = listNewsAdapter

        listNewsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                showSelectedNews(data)
            }
        })
    }

    private fun showSelectedNews(news: News) {
        val moveToDetail = Intent(requireContext(), NewsDetailActivity::class.java)
        moveToDetail.putExtra(NewsDetailActivity.EXTRA_NEWS, news)
        startActivity(moveToDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

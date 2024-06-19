package com.example.emosense.view.forum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.data.response.ForumItem
import com.example.emosense.data.response.ListForumResponse
import com.example.emosense.data.response.ProfileResponse
import retrofit2.Call
import retrofit2.Response

class ForumViewModel(private val repository: UserRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _forumResponse = MutableLiveData<List<ForumItem?>>()
    val forumResponse: LiveData<List<ForumItem?>> = _forumResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getForum() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getAllForum()
        api.enqueue(object : retrofit2.Callback<ListForumResponse> {
            override fun onResponse(call: Call<ListForumResponse>, response: Response<ListForumResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.status.toString()
                    _forumResponse.value = response.body()?.data!!
                    Log.d("forumresponse", forumResponse.toString())
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ListForumResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }
}


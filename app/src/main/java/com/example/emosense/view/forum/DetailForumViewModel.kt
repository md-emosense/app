package com.example.emosense.view.forum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.response.DetailForumResponse
import com.example.emosense.data.response.ForumData
import com.example.emosense.data.response.ForumItem
import com.example.emosense.data.response.ListForumResponse
import retrofit2.Call
import retrofit2.Response

class DetailForumViewModel(private val repository: UserRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _forumResponse = MutableLiveData<ForumData?>()
    val forumResponse: LiveData<ForumData?> = _forumResponse

    fun getForumDetail(id: Int) {
        val api = ApiConfig.getApiService().getForumDetail(id)
        api.enqueue(object : retrofit2.Callback<DetailForumResponse> {
            override fun onResponse(call: Call<DetailForumResponse>, response: Response<DetailForumResponse>) {
                if (response.isSuccessful) {
                    val forumData = response.body()?.data
                    if (forumData != null) {
                        _forumResponse.value = forumData
                        Log.d("DetailForumViewModel", "Forum detail received: $forumData")
                    } else {
                        Log.e("DetailForumViewModel", "Forum detail is null")
                    }
                } else {
                    _message.value = response.message()
                    Log.e("DetailForumViewModel", "Failed to get forum detail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailForumResponse>, t: Throwable) {
                _message.value = t.message.toString()
                Log.e("DetailForumViewModel", "Error getting forum detail: ${t.message}")
            }
        })
    }
}
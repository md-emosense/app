package com.example.emosense.view.forum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.request.ReplyRequest
import com.example.emosense.data.request.UserRequest
import com.example.emosense.data.response.DetailForumResponse
import com.example.emosense.data.response.ForumData
import com.example.emosense.data.response.ForumItem
import com.example.emosense.data.response.ListForumResponse
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.Replies
import com.example.emosense.data.response.ReplyResponse
import retrofit2.Call
import retrofit2.Response

class DetailForumViewModel(private val repository: UserRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _forumResponse = MutableLiveData<ForumData?>()
    val forumResponse: LiveData<ForumData?> = _forumResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

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

    fun postReply(forumId: Int, userId: Int, reply: String, onReplyPosted: (Replies) -> Unit) {
        val request = ReplyRequest(userId, forumId, reply)
        val api = ApiConfig.getApiService().uploadReply(request)
        api.enqueue(object : retrofit2.Callback<ReplyResponse> {
            override fun onResponse(
                call: Call<ReplyResponse>,
                response: Response<ReplyResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val newReplyData = response.body()!!.data
                    if (newReplyData != null) {
                        val newReply = Replies(
                            forumId = newReplyData.forumId,
                            userId = newReplyData.userId,
                            isi = newReplyData.isi
                        )
                        _message.value = "Success"
                        onReplyPosted(newReply)
                    } else {
                        _message.value = "Failed to post reply: response body is null"
                    }
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ReplyResponse>, t: Throwable) {
                _message.value = t.message.toString()
            }
        })
    }


}
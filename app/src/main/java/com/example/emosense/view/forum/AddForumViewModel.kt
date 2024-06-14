package com.example.emosense.view.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.request.AddForumRequest
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.UploadForumResponse
import retrofit2.Call
import retrofit2.Response

class AddForumViewModel(private val repository: UserRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _uploadResponse = MutableLiveData<UploadForumResponse>()
    val uploadResponse: LiveData<UploadForumResponse> = _uploadResponse
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun postForum(id: Int, title: String, body: String){
        val request = AddForumRequest(id,title,body)
        val api = ApiConfig.getApiService().uploadForum(request)
        api.enqueue(object : retrofit2.Callback<UploadForumResponse> {
            override fun onResponse(call: Call<UploadForumResponse>, response: Response<UploadForumResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Success"
                    _uploadResponse.value = response.body()!!
                } else {
                    _message.value = response.message()

                }
            }

            override fun onFailure(call: Call<UploadForumResponse>, t: Throwable) {
                _message.value = t.message.toString()
            }

        })
    }
}
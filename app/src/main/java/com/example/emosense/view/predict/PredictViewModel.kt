package com.example.emosense.view.predict

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel(private val repository: UserRepository) : ViewModel() {

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _message = MutableLiveData<String>()
//    val message: LiveData<String> = _message
//
//    private val _predictResponse = MutableLiveData<FileUploadResponse>()
//    val predictResponse: LiveData<FileUploadResponse> = _predictResponse
//
//    fun getSession(): LiveData<UserModel> {
//        return repository.getSession().asLiveData()
//    }
//
//    fun predict(multipartBody: MultipartBody.Part, requestBody: RequestBody) {
//        _isLoading.value = true
//        val api = ApiConfig.getApiService().predict(requestBody, multipartBody)
//        api.enqueue(object : Callback<FileUploadResponse> {
//            override fun onResponse(
//                call: Call<FileUploadResponse>,
//                response: Response<FileUploadResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        _message.value = responseBody.message
//                        _predictResponse.value = responseBody!!
//                    }
//                } else {
//                    _message.value = response.message()
//
//                }
//            }
//
//            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
//                _isLoading.value = false
//                _message.value = t.message
//            }
//        })
//    }

}
package com.example.emosense.view.predict

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.response.PredictResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _predictResponse = MutableLiveData<PredictResponse>()
    val predictResponse: LiveData<PredictResponse> = _predictResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun predict(multipartBody: MultipartBody.Part, requestBody: RequestBody) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().predict(requestBody, multipartBody)
        api.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _message.value = responseBody.message
                        _predictResponse.value = responseBody!!
                        Log.d("PredictViewModel", "Predict response received1: $responseBody")
                    }
                    Log.d("PredictViewModel", "Predict response received2: $responseBody")
                } else {
                    _message.value = response.message()
                    Log.e("PredictViewModel", "Prediction request failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
                Log.e("PredictViewModel", "Prediction request failed: ${t.message}")

            }
        })
    }

}
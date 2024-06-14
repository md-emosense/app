package com.example.emosense.view.flashcards

import androidx.lifecycle.*
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.response.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class FlashcardsViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _speechResponse = MutableLiveData<List<SpeechItem>>()
    val speechResponse: LiveData<List<SpeechItem>> = _speechResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getAllSpeech() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getAllSpeech()
        api.enqueue(object : retrofit2.Callback<SpeechResponse> {
            override fun onResponse(call: Call<SpeechResponse>, response: Response<SpeechResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.status.toString()
                    _speechResponse.value = response.body()!!.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<SpeechResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }
}

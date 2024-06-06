package com.example.emosense.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.data.response.ListClinicResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _clinicResponse = MutableLiveData<List<ClinicItem>>()
    val clinicResponse: LiveData<List<ClinicItem>> = _clinicResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getAllClinic() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getClinic()
        api.enqueue(object : retrofit2.Callback<ListClinicResponse> {
            override fun onResponse(call: Call<ListClinicResponse>, response: Response<ListClinicResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.status.toString()
                    _clinicResponse.value = response.body()!!.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ListClinicResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }

}
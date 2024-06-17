package com.example.emosense.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.request.UserRequest
import com.example.emosense.data.response.ClinicItem
import com.example.emosense.data.response.ListClinicResponse
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.ProfileResponse
import com.example.emosense.data.response.UserData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _profileResponse = MutableLiveData<UserData>()
    val profileResponse: LiveData<UserData> = _profileResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getProfile(userId: Int) {
        val api = ApiConfig.getApiService().getProfile(userId)
        api.enqueue(object : retrofit2.Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    _message.value = response.body()?.status.toString()
                    _profileResponse.value = response.body()?.data!!
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _message.value = t.message.toString()
            }
        })
    }

    fun checkPassword(email: String, password: String) {
        _isLoading.value = true
        val request = UserRequest(email, password)
        val api = ApiConfig.getApiService().login(request)
        api.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Success"
                } else {
                    when (response.code()) {
                        401 -> _message.value = "Password yang Anda masukkan salah"
                        else -> {
                            _message.value = response.message()
                        }
                    }

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }

        })

    }

}

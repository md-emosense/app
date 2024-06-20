package com.example.emosense.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.emosense.data.api.ApiConfig
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.request.UpdateProfileRequest
import com.example.emosense.data.request.UserRequest
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.RegisterResponse
import com.example.emosense.data.response.UpdateProfileResponse
import com.example.emosense.data.response.UserData
import com.example.emosense.data.response.UserResponse
import retrofit2.Call
import retrofit2.Response

class UpdateDataViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _wrongPassword = MutableLiveData<Boolean?>()
    val wrongPassword: LiveData<Boolean?> = _wrongPassword

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
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
                    _wrongPassword.value = false
                } else {
                    when (response.code()) {
                        401 ->{
                            _wrongPassword.value = true
                            _message.value = "Password yang Anda masukkan salah"
                        }
                        else -> {
                            _message.value = response.message()
                            _wrongPassword.value = true
                        }
                    }

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
                _wrongPassword.value = true
            }

        })

    }

    fun updateChildData(userData: UserData, password: String, id: Int, childName: String, childBirthday: String, childAdhd: String) {
        _isLoading.value = true

        val request = UpdateProfileRequest(id,userData.fullName.toString(),userData.email.toString(),password,childName,childBirthday,childAdhd)
        val api = ApiConfig.getApiService().updateProfile(request)
        api.enqueue(object : retrofit2.Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Data berhasil diubah"
                } else {
                    when (response.code()) {
                        400 -> _message.value =
                            "Data gagal diubah"
                        else -> {
                            _message.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }

        })
    }

    fun editProfile(userData: UserData, password: String, id: Int, name: String, childBirthday: String, email: String){
        _isLoading.value = true

        val request = UpdateProfileRequest(id,name,email,password,userData.childName.toString(),childBirthday,userData.adhdDesc.toString())
        val api = ApiConfig.getApiService().updateProfile(request)
        api.enqueue(object : retrofit2.Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Data berhasil diubah"
                } else {
                    when (response.code()) {
                        400 -> _message.value =
                            "Email sudah pernah digunakan"
                        else -> {
                            _message.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }

        })
    }

    fun changePassword(userData: UserData, password: String, id: Int, childBirthday: String){
        _isLoading.value = true

        val request = UpdateProfileRequest(id,userData.fullName.toString(),userData.email.toString(),password,userData.childName.toString(),childBirthday,userData.adhdDesc.toString())
        val api = ApiConfig.getApiService().updateProfile(request)
        api.enqueue(object : retrofit2.Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Password Anda berhasil diubah"
                } else {
                    when (response.code()) {
                        400 -> _message.value =
                            "Password gagal diubah"
                        else -> {
                            _message.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }

        })
    }

    fun resetWrongPassword() {
        _wrongPassword.value = null
    }

    fun resetMessage() {
        _message.value = ""
    }
}
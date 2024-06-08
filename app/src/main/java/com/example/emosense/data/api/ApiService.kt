package com.example.emosense.data.api


import com.example.emosense.data.request.UserRequest
import com.example.emosense.data.response.ListClinicResponse
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.RegisterResponse
import com.example.emosense.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/signup")
    fun register(@Body request: UserResponse): Call<RegisterResponse>

    @POST("/login")
    fun login(@Body request: UserRequest): Call<LoginResponse>

    @GET("clinic")
    fun getClinic(): Call<ListClinicResponse>
}
package com.example.emosense.data.api


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
//    @POST("signup")
//    fun register(
//        @Field("fullName") fullName: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("childName") childName: String,
//        @Field("adhdDesc") adhdDesc: String,
//        @Field("childBirthday") childBirthday: String,
//        ): Call<RegisterResponse>

    @POST("/signup")
    fun register(@Body request: UserResponse): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("clinic")
    fun getClinic(): Call<ListClinicResponse>
}
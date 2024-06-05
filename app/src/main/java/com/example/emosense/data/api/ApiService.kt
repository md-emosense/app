package com.example.emosense.data.api


import com.example.emosense.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    fun register(
        @Field("fullName") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("childName") childName: String,
        @Field("adhdDesc") adhdDesc: String,
        @Field("childBirthday") childBirthday: String,
        ): Call<RegisterResponse>
}
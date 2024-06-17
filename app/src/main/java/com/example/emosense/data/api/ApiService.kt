package com.example.emosense.data.api


import com.example.emosense.data.request.AddForumRequest
import com.example.emosense.data.request.ReplyRequest
import com.example.emosense.data.request.UpdateProfileRequest
import com.example.emosense.data.request.UserRequest
import com.example.emosense.data.response.DetailForumResponse
import com.example.emosense.data.response.ListClinicResponse
import com.example.emosense.data.response.ListForumResponse
import com.example.emosense.data.response.LoginResponse
import com.example.emosense.data.response.PredictResponse
import com.example.emosense.data.response.ProfileResponse
import com.example.emosense.data.response.RegisterResponse
import com.example.emosense.data.response.ReplyResponse
import com.example.emosense.data.response.SpeechResponse
import com.example.emosense.data.response.UpdateProfileResponse
import com.example.emosense.data.response.UploadForumResponse
import com.example.emosense.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/signup")
    fun register(@Body request: UserResponse): Call<RegisterResponse>

    @POST("/login")
    fun login(@Body request: UserRequest): Call<LoginResponse>

    @GET("profile/{id}")
    fun getProfile(
        @Path("id") id: Int
    ): Call<ProfileResponse>

    @GET("clinic")
    fun getClinic(): Call<ListClinicResponse>

    @GET("forum")
    fun getAllForum(): Call<ListForumResponse>

    @GET("speech")
    fun getAllSpeech(): Call<SpeechResponse>

    @GET("forum/{id}")
    fun getForumDetail(
        @Path("id") id: Int
    ): Call<DetailForumResponse>

    @POST("forum/upload")
    fun uploadForum(
        @Body forumRequest: AddForumRequest
    ): Call<UploadForumResponse>

    @POST("reply/upload")
    fun uploadReply(
        @Body replyRequest: ReplyRequest
    ): Call<ReplyResponse>

    @PUT("profile/update")
    fun updateProfile(
        @Body requestBody: UpdateProfileRequest
    ): Call<UpdateProfileResponse>

    @Multipart
    @POST("predict")
    fun predict(
        @Part("userId") id: RequestBody,
        @Part file: MultipartBody.Part,
    ): Call<PredictResponse>

}
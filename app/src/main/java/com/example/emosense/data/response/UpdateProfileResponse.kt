package com.example.emosense.data.response
import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProfileData?
)

data class ProfileData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("childName")
    val childName: String,
    @SerializedName("childBirthday")
    val childBirthday: String,
    @SerializedName("adhdDesc")
    val adhdDesc: String
)

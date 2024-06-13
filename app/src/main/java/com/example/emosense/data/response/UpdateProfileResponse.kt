package com.example.emosense.data.response

data class UpdateProfileResponse(
    val status: String,
    val message: String,
    val data: ProfileData?
)

data class ProfileData(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val childName: String,
    val childBirthday: String,
    val adhdDesc: String
)
package com.example.emosense.data.request

data class UpdateProfileRequest(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val childName: String,
    val childBirthday: String,
    val adhdDesc: String
)
package com.example.emosense.data.preferences

data class UserModel(
    val name: String,
    val id: Int,
    val isLogin: Boolean = false
)
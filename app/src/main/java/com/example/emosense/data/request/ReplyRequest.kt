package com.example.emosense.data.request

data class ReplyRequest(
    val userId: Int,
    val forumId: String,
    val isi: String
)
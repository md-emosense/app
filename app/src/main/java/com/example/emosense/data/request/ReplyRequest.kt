package com.example.emosense.data.request

data class ReplyRequest(
    val userId: Int,
    val forumId: Int,
    val isi: String
)
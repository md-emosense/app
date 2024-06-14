package com.example.emosense.data.response

data class UploadForumResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class UploadData(
	val judul: String? = null,
	val userId: Int? = null,
	val isi: String? = null
)


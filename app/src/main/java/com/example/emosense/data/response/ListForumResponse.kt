package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class ListForumResponse(

	@field:SerializedName("data")
	val data: List<ForumItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ForumItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("isi")
	val isi: String? = null
)

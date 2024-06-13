package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class ReplyResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
//Data -> ReplyData
data class ReplyData(

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("isi")
	val isi: String? = null,

	@field:SerializedName("forumId")
	val forumId: Int? = null
)

package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class DetailForumResponse(

	@field:SerializedName("data")
	val data: ForumData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Replies(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("forumId")
	val forumId: Int? = null,

	@field:SerializedName("isi")
	val isi: String? = null
)

data class Forum(

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
//Data -> ForumData (double predict response)
data class ForumData(

	@field:SerializedName("forum")
	val forum: Forum? = null,

	@field:SerializedName("replies")
	val replies: Replies? = null
)

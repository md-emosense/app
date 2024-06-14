package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class SpeechResponse(

	@field:SerializedName("data")
	val data: List<SpeechItem> = emptyList(),

	@field:SerializedName("status")
	val status: String? = null
)

data class SpeechItem(

	@field:SerializedName("url_audio")
	val urlAudio: String,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("word")
	val word: String,
)

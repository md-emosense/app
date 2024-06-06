package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class ListClinicResponse(

	@field:SerializedName("data")
	val data: List<ClinicItem> = emptyList(),

	@field:SerializedName("status")
	val status: String? = null
)

package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("childName")
	val childName: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("adhdDesc")
	val adhdDesc: String? = null,

	@field:SerializedName("childBirthday")
	val childBirthday: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

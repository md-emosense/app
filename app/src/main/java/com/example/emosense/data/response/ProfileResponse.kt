package com.example.emosense.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProfileResponse(

	@field:SerializedName("data")
	val data: UserData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class UserData(

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
): Parcelable

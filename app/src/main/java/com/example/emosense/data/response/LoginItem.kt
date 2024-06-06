package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class LoginItem(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("childName")
    val childName: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("adhdDesc")
    val adhdDesc: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("childBirthday")
    val childBirthday: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
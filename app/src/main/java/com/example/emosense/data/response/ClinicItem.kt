package com.example.emosense.data.response

import com.google.gson.annotations.SerializedName

data class ClinicItem(

    @field:SerializedName("clinicName")
    val clinicName: String? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("streetAddress")
    val streetAddress: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("suburb")
    val suburb: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("picture")
    val picture: String? = null
)
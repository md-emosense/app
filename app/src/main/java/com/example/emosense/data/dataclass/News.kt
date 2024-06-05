package com.example.emosense.data.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val description: String,
    val photo: Int
    //apalagi?
): Parcelable

package com.example.emosense.di

import android.content.Context
import com.example.emosense.data.database.UserRepository
import com.example.emosense.data.preferences.UserPreferences
import com.example.emosense.data.preferences.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}
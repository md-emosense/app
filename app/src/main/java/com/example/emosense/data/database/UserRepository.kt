package com.example.emosense.data.database

import com.example.emosense.data.api.ApiService
import com.example.emosense.data.preferences.UserModel
import com.example.emosense.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreferences
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreferences
        ) = UserRepository(userPreference)
    }
}
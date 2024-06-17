package com.example.emosense.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emosense.data.database.UserRepository
import com.example.emosense.di.Injection
import com.example.emosense.view.flashcards.FlashcardsViewModel
import com.example.emosense.view.forum.AddForumViewModel
import com.example.emosense.view.forum.DetailForumViewModel
import com.example.emosense.view.forum.ForumViewModel
import com.example.emosense.view.login.LoginViewModel
import com.example.emosense.view.main.MainViewModel
import com.example.emosense.view.predict.PredictViewModel
import com.example.emosense.view.profile.ProfileViewModel
import com.example.emosense.view.profile.UpdateDataViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> {
                ForumViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddForumViewModel::class.java) -> {
                AddForumViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FlashcardsViewModel::class.java) -> {
                FlashcardsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailForumViewModel::class.java) -> {
                DetailForumViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateDataViewModel::class.java) -> {
                UpdateDataViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}
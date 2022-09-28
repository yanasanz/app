package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class SignInViewModel(application: Application) : AndroidViewModel(application) {

        private val repository: PostRepository =
            PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    fun signIn(login: String, pass: String) = viewModelScope.launch {
        val response = repository.signIn(login, pass)
        response.token?.let{
            AppAuth.getInstance().setAuth(response.id, response.token)
        }
    }
}
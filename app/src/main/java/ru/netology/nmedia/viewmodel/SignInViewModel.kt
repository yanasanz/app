package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: PostRepository,
    private val auth: AppAuth
) : ViewModel() {

    fun signIn(login: String, pass: String) = viewModelScope.launch {
        val response = repository.signIn(login, pass)
        response.token?.let {
            auth.setAuth(response.id, response.token)
        }
    }
}
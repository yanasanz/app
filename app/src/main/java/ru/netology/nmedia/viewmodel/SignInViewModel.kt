package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.dto.UserCredentials
import ru.netology.nmedia.repository.PostRepository
import java.lang.RuntimeException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: PostRepository) :
    ViewModel() {

    private val _data: MutableLiveData<UserCredentials> = MutableLiveData<UserCredentials>()
    val data: LiveData<UserCredentials>
        get() = _data

    fun signIn(login: String, pass: String) = viewModelScope.launch {
        val response = repository.signIn(login, pass)
        val body = response.token?.let { UserCredentials(response.id, it) }
            ?: throw RuntimeException("Body is null")
        _data.value = body
    }
}
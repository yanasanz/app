package ru.netology.nmedia.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.repository.PostRepository
import java.io.File
import java.lang.RuntimeException
import javax.inject.Inject

private val noAvatar = PhotoModel()

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: PostRepository,
    private val auth: AppAuth
) : ViewModel() {

    private val _avatar = MutableLiveData(noAvatar)
    val avatar: LiveData<PhotoModel>
        get() = _avatar

    fun register(login: String, pass: String, name: String) = viewModelScope.launch {
        val response = repository.register(login, pass, name)
        response.token?.let {
            auth.setAuth(response.id, response.token)
        }
    }

    fun registerWithPhoto(login: String, pass: String, name: String, media: MediaUpload) =
        viewModelScope.launch {
            val response = repository.registerWithPhoto(login, pass, name, media)
            response.token?.let {
                auth.setAuth(response.id, response.token)
            }
        }

    fun changeAvatar(uri: Uri?, file: File?) {
        _avatar.value = PhotoModel(uri, file)
    }
}
package ru.netology.nmedia.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.UserCredentials
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.repository.PostRepository
import java.io.File
import java.lang.RuntimeException
import javax.inject.Inject

private val noAvatar = PhotoModel()

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: PostRepository,
    val auth: AppAuth
) : ViewModel() {

    private val _avatar = MutableLiveData(noAvatar)
    val avatar: LiveData<PhotoModel>
        get() = _avatar

    private val _data: MutableLiveData<UserCredentials> = MutableLiveData<UserCredentials>()
    val data: LiveData<UserCredentials>
        get() = _data

    fun register(login: String, pass: String, name: String) = viewModelScope.launch {
        val response = repository.register(login, pass, name)
        val body = response.token?.let {
            UserCredentials(response.id, it)
        } ?: throw RuntimeException("Body is null")
        _data.value = body
    }

    fun registerWithPhoto(login: String, pass: String, name: String, media: MediaUpload) =
        viewModelScope.launch {
            val response = repository.registerWithPhoto(login, pass, name, media)
            val body = response.token?.let {
                UserCredentials(response.id, it)
            } ?: throw RuntimeException("Body is null")
            _data.value = body
        }

    fun changeAvatar(uri: Uri?, file: File?) {
        _avatar.value = PhotoModel(uri, file)
    }
}
package ru.netology.nmedia.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import java.io.File

private val noAvatar = PhotoModel()

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _avatar = MutableLiveData(noAvatar)
    val avatar: LiveData<PhotoModel>
        get() = _avatar

    fun register(login: String, pass: String, name: String) = viewModelScope.launch {
        val response = repository.register(login, pass, name)
        response.token?.let {
            AppAuth.getInstance().setAuth(response.id, response.token)
        }
    }

    fun registerWithPhoto(login: String, pass: String, name: String, media: MediaUpload) =
        viewModelScope.launch {
            val response = repository.registerWithPhoto(login, pass, name, media)
            response.token?.let {
                AppAuth.getInstance().setAuth(response.id, response.token)
            }
        }

    fun changeAvatar(uri: Uri?, file: File?) {
        _avatar.value = PhotoModel(uri, file)
    }
}
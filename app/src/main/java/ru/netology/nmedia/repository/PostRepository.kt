package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post

interface PostRepository {

    val data: Flow<List<Post>>
    suspend fun getAll()
    suspend fun save(post: Post)
    suspend fun likeById(id: Long)
    suspend fun deleteLikeById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun getPostById(id: Long)
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun read()
    suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
    suspend fun upload(upload: MediaUpload): Media
    suspend fun signIn(login: String, pass: String): AuthState
    suspend fun register(login: String, pass: String, name: String): AuthState
    suspend fun registerWithPhoto(
        login: String,
        pass: String,
        name: String,
        media: MediaUpload
    ): AuthState
}
package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
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
    suspend fun getPostById(id:Long)
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun read()
    suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
    suspend fun upload(upload: MediaUpload): Media
}
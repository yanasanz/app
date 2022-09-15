package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getAll(callback: Callback<List<Post>>)
    fun save(post: Post, callback: Callback<Post>)
    fun likeById(id: Long, callback: Callback<Post>)
    fun deleteLikeById(id: Long, callback: Callback<Post>)
    fun shareById(id: Long)
    fun removeById(id: Long, callback: Callback<Unit>)
    fun getPostById(id:Long, callback: Callback<Post>)

    interface Callback<T> {
        fun onSuccess(posts: T) {}
        fun onError(e: Exception) {}
    }
}
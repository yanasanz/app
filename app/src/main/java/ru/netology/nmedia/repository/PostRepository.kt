package ru.netology.nmedia.repository

import android.service.autofill.SaveCallback
import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getAllAsync(callback: GetAllCallback)
    fun save(post: Post, callback: SaveCallback)
    fun likeById(id: Long, callback: LikeByIdCallback)
    fun deleteLikeById(id: Long, callback: DeleteLikeByIdCallback)
    fun shareById(id: Long)
    fun removeById(id: Long, callback: RemoveByIdCallback)
    fun getPostById(id:Long, callback: GetPostByIdCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface SaveCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

    interface LikeByIdCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

    interface DeleteLikeByIdCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

    interface RemoveByIdCallback {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

    interface GetPostByIdCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }
}
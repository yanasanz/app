package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl : PostRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        private val jsonType = "application/json".toMediaType()
    }

    override fun getAll(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

    override fun deleteLikeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.deleteLikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

    override fun shareById(id: Long) {
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: retrofit2.Call<Unit>,
                response: retrofit2.Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

    override fun getPostById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.getPostById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    callback.onError(SocketTimeoutException("There was an error connecting to the server"))
                } else {
                    callback.onError(Exception("Loading error"))
                }
            }
        })
    }

}
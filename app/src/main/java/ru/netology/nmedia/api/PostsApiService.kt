package ru.netology.nmedia.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.dto.Post

private val BASE_URL = "${BuildConfig.BASE_URL}/api/"

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttp)
    .baseUrl(BASE_URL)
    .build()

interface PostsApiService {
    @GET("posts")
    fun getAll(): Call<List<Post>>

    @POST("posts")
    fun save(@Body post: Post): Call<Post>

    @POST("posts/{id}/likes")
    fun likeById(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}/likes")
    fun deleteLikeById(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}")
    fun removeById(@Path("id") id: Long): Call<Unit>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Long): Call<Post>

}

object PostsApi {
    val retrofitService: PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java)
    }
}
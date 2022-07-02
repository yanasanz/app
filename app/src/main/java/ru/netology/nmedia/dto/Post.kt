package ru.netology.nmedia.dto

import android.media.Image

data class Post(
    val id: Long,
    val avatar: Image? = null,
    val author: String,
    val content: String,
    val published: String,
    val video: String = "",
    val likedByMe: Boolean = false,
    val likesAmount: Int = 0,
    val sharedByMe: Boolean = false,
    val sharesAmount: Int = 0,
    val viewsAmount: Int = 0
)

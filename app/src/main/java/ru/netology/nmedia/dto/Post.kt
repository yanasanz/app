package ru.netology.nmedia.dto

import android.media.Image

data class Post(
    val id: Long,
    val avatar: Image?,
    val author: String,
    val content: String,
    val published: String,
    val video: String,
    val likedByMe: Boolean,
    val likesAmount: Int,
    val sharedByMe: Boolean,
    val sharesAmount: Int,
    val viewsAmount: Int,
    val isDeleted: Boolean = false
)

package ru.netology.nmedia.dto

import android.media.Image
import android.provider.MediaStore

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
    val viewsAmount: Int
)

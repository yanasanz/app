package ru.netology.nmedia.dto

import android.media.Image

data class Post(
    val id: Long,
    val avatar: Image?,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likesAmount: Int,
    var sharesAmount: Int,
    var viewsAmount: Int
)

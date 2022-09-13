package ru.netology.nmedia.dto

import android.media.Image

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val video: String? = "",
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val sharedByMe: Boolean = false,
    val sharesAmount: Int = 0,
    val viewsAmount: Int = 0,
    val authorAvatar: String? = null,
    var attachment: Attachment? = null,
)

data class Attachment(
    val url: String,
    val description: String?,
    val type: AttachmentType,
)

enum class AttachmentType {
    IMAGE
}

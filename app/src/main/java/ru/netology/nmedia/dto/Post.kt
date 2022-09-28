package ru.netology.nmedia.dto

import ru.netology.nmedia.enumeration.AttachmentType

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val authorAvatar: String? = null,
    var isRead: Boolean = false,
    var attachment: Attachment? = null,
)

data class Attachment(
    val url: String,
    val type: AttachmentType,
)


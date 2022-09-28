package ru.netology.nmedia.dto

import java.io.File

data class Media(val id: String)

data class MediaUpload(var file: File)

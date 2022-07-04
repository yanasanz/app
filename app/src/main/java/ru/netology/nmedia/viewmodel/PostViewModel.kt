package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*
import java.text.DateFormat

private val empty = Post(
    id = 0L,
    avatar = null,
    author = "Нетология",
    content = "",
    published = "${DateFormat.getDateTimeInstance().format(System.currentTimeMillis())}",
    video = "",
    likedByMe = false,
    likesAmount = 0,
    sharedByMe = false,
    sharesAmount = 0,
    viewsAmount = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(context = application).postDao()
    )
    val data = repository.getAll()
    private val edited = MutableLiveData(empty)

    fun save(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value?.let {
            repository.save(it.copy(content = text))
        }
        edited.value = empty
    }

    fun onLike(post: Post) {
        repository.likeById(post.id)
    }

    fun onShare(post: Post) {
        repository.shareById(post.id)
    }

    fun onRemove(post: Post) {
        repository.removeById(post.id)
    }

    fun onEdit(post: Post) {
        edited.value = post
    }

    fun onCancel() {
        edited.value = null
    }
}
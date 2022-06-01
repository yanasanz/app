package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*

private val empty = Post(
    id = 0L,
    avatar = null,
    author = "",
    content = "",
    published = "",
    video = "",
    likedByMe = false,
    likesAmount = 0,
    sharedByMe = false,
    sharesAmount = 0,
    viewsAmount = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

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
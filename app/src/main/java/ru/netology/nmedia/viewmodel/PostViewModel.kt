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

class PostViewModel : ViewModel(), PostInteractionListener {
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

    // region PostInteractionListener implementation

    override fun onLike(post: Post) {
        repository.likeById(post.id)
    }

    override fun onShare(post: Post) {
        repository.shareById(post.id)
    }

    override fun onRemove(post: Post) {
        repository.removeById(post.id)
    }

    override fun onEdit(post: Post) {
        edited.value = post
    }

    override fun onCancel() {
        edited.value = null
    }

    override fun onWatch(post: Post) {
        repository.watchVideo(post)
    }

    // endregion PostInteractionListener
}
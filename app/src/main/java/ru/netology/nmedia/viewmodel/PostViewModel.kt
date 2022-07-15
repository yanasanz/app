package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import java.text.DateFormat
import kotlin.concurrent.thread

private val empty = Post(
    id = 0L,
    avatar = null,
    author = "Нетология",
    content = "",
    published = "",
    video = "",
    likedByMe = false,
    likes = 0,
    sharedByMe = false,
    sharesAmount = 0,
    viewsAmount = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun save(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value?.let {
            thread {
                repository.save(it.copy(content = text))
                _postCreated.postValue(Unit)
            }
        }
        edited.value = empty
    }

    fun onLike(post: Post) {
        thread {
            val updatedPost: Post =
                if (post.likedByMe) repository.deleteLikeById(post.id) else repository.likeById(post.id)
            _data.postValue(
                FeedModel(
                    _data.value!!.posts.map {
                        if (post.id == it.id) updatedPost else it
                    }
                )
            )
            repository.getAll()
        }
    }

    fun onShare(post: Post) {
        thread {
            repository.shareById(post.id)
        }
    }

    fun onRemove(post: Post) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != post.id }
                )
            )
            try {
                repository.removeById(post.id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }

        }
    }

    fun onEdit(post: Post) {
        edited.value = post
    }

    fun onCancel() {
        edited.value = null
    }
}
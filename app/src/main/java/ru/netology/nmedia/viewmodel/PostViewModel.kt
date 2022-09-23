package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0L,
    author = "Нетология",
    content = "",
    published = "",
    video = "",
    likedByMe = false,
    likes = 0,
    sharedByMe = false,
    sharesAmount = 0,
    viewsAmount = 0,
    authorAvatar = null
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
        _data.value = FeedModel(loading = true)
        repository.getAll(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(
                    if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                )
            }
        })
    }

    fun save(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value?.let {
            repository.save(it.copy(content = text), object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    _data.postValue(FeedModel())
                    _postCreated.postValue(Unit)
                    loadPosts()
                }

                override fun onError(e: Exception) {
                    _data.postValue(
                        if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                    )
                }
            })
        }
        edited.value = empty
    }

    fun onLike(post: Post) {

        repository.getPostById(post.id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                if (posts.likedByMe) {
                    repository.deleteLikeById(
                        posts.id,
                        object : PostRepository.Callback<Post> {
                            override fun onSuccess(posts: Post) {
                                _data.postValue(
                                    FeedModel(posts = _data.value!!.posts.map {
                                        if (posts.id == it.id) {
                                            posts.copy(
                                                likedByMe = posts.likedByMe,
                                                likes = posts.likes
                                            )
                                        } else {
                                            it
                                        }
                                    })
                                )
                            }

                            override fun onError(e: Exception) {
                                _data.postValue(
                                    if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                                )
                            }
                        })
                } else {
                    repository.likeById(posts.id, object : PostRepository.Callback<Post> {
                        override fun onSuccess(posts: Post) {
                            _data.postValue(
                                FeedModel(posts = _data.value!!.posts.map {
                                    if (posts.id == it.id) {
                                        posts.copy(likedByMe = posts.likedByMe, likes = posts.likes)
                                    } else {
                                        it
                                    }
                                })
                            )
                        }

                        override fun onError(e: Exception) {
                            _data.postValue(
                                if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                            )
                        }
                    })
                }
            }

            override fun onError(e: Exception) {
                _data.postValue(
                    if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                )
            }
        })
    }

    fun onShare(post: Post) {
        thread {
            repository.shareById(post.id)
        }
    }

    fun onRemove(post: Post) {
        val old = _data.value?.posts.orEmpty()

        repository.removeById(post.id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(posts: Unit) {
                try {
                    _data.postValue(
                        _data.value?.copy(posts = _data.value?.posts.orEmpty()
                            .filter { it.id != post.id })
                    )
                } catch (e: IOException) {
                    _data.postValue(_data.value?.copy(posts = old))
                }
            }

            override fun onError(e: Exception) {
                _data.postValue(
                    if (e is SocketTimeoutException) FeedModel(serverError = true) else FeedModel(error = true)
                )
            }
        })
    }

    fun onEdit(post: Post) {
        edited.value = post
    }

    fun onCancel() {
        edited.value = null
    }
}
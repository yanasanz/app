package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 2,
            avatar = null,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях",
            published = "18 сентября в 10:12",
            likedByMe = false,
            likesAmount = 154,
            sharesAmount = 75,
            viewsAmount = 0
        ),
        Post(
            id = 1,
            avatar = null,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb ",
            published = "21 мая в 18:36",
            likedByMe = false,
            likesAmount = 1399,
            sharesAmount = 999,
            viewsAmount = 0
        ),
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likesAmount = if (!it.likedByMe) it.likesAmount + 1 else it.likesAmount - 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { if (it.id != id) it else it.copy(sharesAmount = it.sharesAmount + 1) }
        data.value = posts
    }
}
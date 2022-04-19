package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl: PostRepository {
    private var post = Post(
        id = 1,
        avatar = null,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb ",
        published = "21 мая в 18:36",
        likedByMe = false,
        likesAmount = 1599,
        sharesAmount = 999,
        viewsAmount = 0
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
    }
    override fun share() {
        post = post.copy(sharesAmount = post.sharesAmount + 1)
        data.value = post
    }
}
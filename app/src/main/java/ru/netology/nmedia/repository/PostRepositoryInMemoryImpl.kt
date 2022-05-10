package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId = 10L

    override val data: MutableLiveData<List<Post>>

    init {
        val posts = listOf(
            Post(
                id = 9,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Освоение новой профессии - это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни, менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки -> http://netolo.gy/fPD",
                published = "23 сентября в 10:12",
                likedByMe = false,
                likesAmount = 589,
                sharedByMe = false,
                sharesAmount = 42,
                viewsAmount = 0
            ),
            Post(
                id = 8,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали",
                published = "22 сентября в 10:14",
                likedByMe = false,
                likesAmount = 56456,
                sharedByMe = false,
                sharesAmount = 453,
                viewsAmount = 0
            ),
            Post(
                id = 7,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Таймбоксинг - отличный способ навести порядок в своем календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип - на каждое дело заранее выделяется определенный отрезок времени. В это время вы работаете только над одной задачей, не переключаась на другие. Собрали советы, которые помогут внедрить таймбоксинг",
                published = "22 сентября в 10:12",
                likedByMe = false,
                likesAmount = 58,
                sharedByMe = false,
                sharesAmount = 63,
                viewsAmount = 0
            ),
            Post(
                id = 6,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "24 сентября стартует новый поток бесплатного курса \"Диджитал-старт: первый шаг к востребованной профессии\" - за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам -> http://netolo.gy/fQ",
                published = "21 сентября в 10:12",
                likedByMe = false,
                likesAmount = 4582,
                sharedByMe = false,
                sharesAmount = 4835,
                viewsAmount = 0
            ),
            Post(
                id = 5,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения",
                published = "20 сентября в 10:14",
                likedByMe = false,
                likesAmount = 452,
                sharedByMe = false,
                sharesAmount = 135,
                viewsAmount = 0
            ),
            Post(
                id = 4,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска",
                published = "19 сентября в 14:12",
                likedByMe = false,
                likesAmount = 78,
                sharedByMe = false,
                sharesAmount = 366,
                viewsAmount = 0
            ),
            Post(
                id = 3,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript",
                published = "19 сентября в 10:24",
                likedByMe = false,
                likesAmount = 473,
                sharedByMe = false,
                sharesAmount = 287,
                viewsAmount = 0
            ),
            Post(
                id = 2,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях",
                published = "18 сентября в 10:12",
                likedByMe = false,
                likesAmount = 154,
                sharedByMe = false,
                sharesAmount = 75,
                viewsAmount = 0
            ),
            Post(
                id = 1,
                avatar = null,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
                published = "21 мая в 18:36",
                likedByMe = false,
                likesAmount = 1399,
                sharedByMe = false,
                sharesAmount = 999,
                viewsAmount = 0
            ),
        )
        data = MutableLiveData(posts)
    }

    private val posts
        get() = checkNotNull(data.value) {
            "Live data should be initialized with posts"
        }

    override fun likeById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likesAmount = if (!it.likedByMe) it.likesAmount + 1 else it.likesAmount - 1
            )
        }
    }

    override fun shareById(id: Long) {
        data.value =
            posts.map { if (it.id != id) it else it.copy(sharedByMe = true, sharesAmount = it.sharesAmount + 1) }
    }

    override fun removeById(id: Long) {
        data.value = posts.filterNot { it.id == id }
    }

    override fun save(post: Post) =
        if (post.id == NEW_POST_ID) insert(post) else update(post)

    private fun insert(post: Post) {
        val identifiedPost = post.copy(id = nextId++)
        data.value = listOf(identifiedPost) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        private const val NEW_POST_ID = 0L
    }
}
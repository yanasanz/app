package ru.netology.nmedia

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            avatar = null,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb ",
            published = "21 мая в 18:36",
            likedByMe = false,
            likesAmount = 1599,
            sharesAmount = 999,
            viewsAmount = 0
        )
        with(binding) {
            author.text = post.author
            avatar.setImageResource(R.drawable.post_avatar_drawable)
            published.text = post.published
            content.text = post.content
            likesAmount.text = displayCount(post.likesAmount)
            sharesAmount.text = displayCount(post.sharesAmount)
            viewsAmount.text = post.viewsAmount.toString()

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked_24)
            }

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likesAmount.text =
                    if (post.likedByMe) displayCount(post.likesAmount + 1) else displayCount(post.likesAmount)
            }

            share?.setOnClickListener {
                post.sharesAmount += 1
                sharesAmount.text = displayCount(post.sharesAmount)
            }
        }
    }
}

fun displayCount(count: Int): String {
    val result = when {
        count in 0..999 -> count.toString()
        count in 1000..9999 -> "${count / 1000}.${count / 100 % 10}K"
        count in 10000..999999 -> "${count / 1000}K"
        count in 1000000..2147483647 -> "${count / 1000000}.${count / 100000 % 10}М"
        else -> "Amount is too big"
    }
    return result
}
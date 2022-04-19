package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                avatar.setImageResource(R.drawable.post_avatar_drawable)
                published.text = post.published
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likesAmount.text =
                    if (post.likedByMe) displayCount(post.likesAmount + 1) else displayCount(post.likesAmount)
                sharesAmount.text = displayCount(post.sharesAmount)
            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
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
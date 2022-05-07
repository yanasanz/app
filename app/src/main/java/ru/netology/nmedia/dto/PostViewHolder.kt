package ru.netology.nmedia.dto

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root){
    fun bind(post:Post){
        binding.apply {
            author.text = post.author
            avatar.setImageResource(R.drawable.post_avatar_drawable)
            published.text = post.published
            content.text = post.content
            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            like.setOnClickListener {
                onLikeListener(post)
            }
            likesAmount.text = displayCount(post.likesAmount)
            share.setOnClickListener {
                onShareListener(post)
            }
            sharesAmount.text = displayCount(post.sharesAmount)
        }
    }

    private fun displayCount(count: Int): String {
        return when (count) {
            in 0..999 -> count.toString()
            in 1000..9999 -> "${count / 1000}.${count / 100 % 10}K"
            in 10000..999999 -> "${count / 1000}K"
            in 1000000..2147483647 -> "${count / 1000000}.${count / 100000 % 10}М"
            else -> "Amount is too big"
        }
    }
}
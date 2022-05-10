package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var post: Post

    private val popupMenu by lazy {
        PopupMenu(itemView.context, binding.menu).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onRemove(post)
                        true
                    }
                    R.id.edit -> {
                        listener.onEdit(post)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    init {
        binding.like.setOnClickListener {
            listener.onLike(post)
        }
        binding.share.setOnClickListener {
            listener.onShare(post)
        }
        binding.menu.setOnClickListener {
            popupMenu.show()
        }
    }

    fun bind(post: Post) {
        this.post = post
        with(binding) {
            author.text = post.author
            avatar.setImageResource(R.drawable.post_avatar_drawable)
            published.text = post.published
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = displayCount(post.likesAmount)
            share.isChecked = post.sharedByMe
            share.text = displayCount(post.sharesAmount)
        }
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
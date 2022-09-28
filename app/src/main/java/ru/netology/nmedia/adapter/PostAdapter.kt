package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.*
import ru.netology.nmedia.view.load
import ru.netology.nmedia.view.loadCircleCrop

interface PostInteractionListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShowPhoto(post: Post)
    fun onCancel()
}

class PostAdapter(private val interactionListener: PostInteractionListener) :
    ListAdapter<Post, PostViewHolder>(DiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {

        val avatarUrl = "${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}"
        val imageUrl = "${BuildConfig.BASE_URL}/media/${post.attachment?.url}"

        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = displayCount(post.likes)
            avatar.loadCircleCrop(avatarUrl)
            if (post.attachment != null) {
                imageAttachment.load(imageUrl)
                imageAttachment.visibility = View.VISIBLE
            } else {
                imageAttachment.visibility = View.GONE
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
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
                }.show()
            }

            like.setOnClickListener {
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }

            imageAttachment.setOnClickListener{
                listener.onShowPhoto(post)
            }

        }
    }
}

private fun displayCount(count: Int): String {
    return when (count) {
        in 0..999 -> count.toString()
        in 1000..9999 -> "${count / 1000}.${count / 100 % 10}K"
        in 10000..999999 -> "${count / 1000}K"
        in 1000000..2147483647 -> "${count / 1000000}.${count / 100000 % 10}Рњ"
        else -> "Amount is too big"
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
package ru.netology.nmedia.adapter


import android.view.View.VISIBLE
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                    R.id.edit -> {
                        listener.onEdit(post)
                        true
                    }
                    R.id.remove -> {
                        listener.onRemove(post)
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
        binding.video.setOnClickListener {
            listener.onWatch(post)
        }
        binding.playButton.setOnClickListener {
            listener.onWatch(post)
        }
    }

    fun bind(post: Post) {

        val avatarUrl = "http://10.0.2.2:9999/avatars/${post.authorAvatar}"
        val attachmentUrl = "http://10.0.2.2:9999/images/${post.attachment?.url}"

        this.post = post
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = displayCount(post.likes)
            share.isChecked = post.sharedByMe
            share.text = displayCount(post.sharesAmount)
            if (post.attachment != null) {
                Glide.with(imageAttachment)
                    .load(attachmentUrl)
                    .into(imageAttachment)
                imageAttachment.visibility = VISIBLE
                imageAttachment.contentDescription = post.attachment?.description
            }

            Glide.with(avatar)
                .load(avatarUrl)
                .placeholder(R.drawable.ic_loading_24)
                .error(R.drawable.ic_baseline_error_24)
                .circleCrop()
                .into(avatar)

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


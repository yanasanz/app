package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.db.dao.PostDao
import ru.netology.nmedia.db.entity.PostEntity
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl(private val dao: PostDao) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            Post(
                id = it.id,
                author = it.author,
                content = it.content,
                published = it.published,
                likedByMe = it.likedByMe,
                likesAmount = it.likesAmount,
                sharedByMe = it.sharedByMe,
                sharesAmount = it.sharesAmount
            )
        }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        dao.save(fromDto(post))
    }

    private fun fromDto(post: Post) = PostEntity(
        id = post.id,
        author = post.author,
        content = post.content,
        published = post.published,
        likedByMe = post.likedByMe,
        likesAmount = post.likesAmount,
        sharedByMe = post.sharedByMe,
        sharesAmount = post.sharesAmount
    )
}
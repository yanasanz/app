package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val likesChannelId = "like"
    private val newPostsChannelId = "newPost"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()

        createNewNotificationChannels()

    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data[action]?.let {
            when (it) {
                likesChannelId -> handleLike(
                    gson.fromJson(
                        message.data[content],
                        Like::class.java
                    )
                )
                newPostsChannelId -> handleNewPost(
                    gson.fromJson(
                        message.data[content],
                        NewPost::class.java
                    )
                )
                else -> return@let
            }
        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }

    private fun createNewNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val likesChannelName = getString(R.string.channel_likes_name)
            val newPostsChannelName = getString(R.string.channel_new_posts_name)
            val likesChannelDescriptionText = getString(R.string.channel_likes_description)
            val newPostsChannelDescriptionText = getString(R.string.channel_likes_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val likesChannel =
                NotificationChannel(likesChannelId, likesChannelName, importance).apply {
                    description = likesChannelDescriptionText
                }
            val newPostsChannel =
                NotificationChannel(newPostsChannelId, newPostsChannelName, importance).apply {
                    description = newPostsChannelDescriptionText
                }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(likesChannel)
            manager.createNotificationChannel(newPostsChannel)
        }
    }

    private fun handleLike(content: Like) {
        val notification = NotificationCompat.Builder(this, likesChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName,
                    content.postAuthor
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handleNewPost(content: NewPost) {
        val notification = NotificationCompat.Builder(this, newPostsChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_new_post_added,
                    content.userName
                )
            )
            .setContentText(content.postContent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content.postContent)
                    .setSummaryText(content.postContent)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }
}

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String
)

data class NewPost(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postContent: String
)
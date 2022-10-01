package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.ui.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.DialogManager
import ru.netology.nmedia.viewmodel.PostViewModel
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class FeedFragment : Fragment() {
    @Inject
    lateinit var auth: AppAuth

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostAdapter(object : PostInteractionListener {
            override fun onLike(post: Post) {
                if (auth.authStateFlow.value.id != 0L) {
                    viewModel.onLike(post)
                } else {
                    DialogManager.SignInDialog(requireContext(), object : DialogManager.Listener {
                        override fun onClick() {
                            findNavController().navigate(R.id.action_feedFragment_to_signInFragment)
                        }
                    })
                }
                viewModel.onLike(post)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.onRemove(post)
            }

            override fun onEdit(post: Post) {
                viewModel.onEdit(post)
                val text = post.content
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = text
                    })
            }

            override fun onShowPhoto(post: Post) {
                val likes = post.likes.toString()
                val id = post.id
                val likedByMe = post.likedByMe
                val url = post.attachment!!.url
                val bundle = Bundle()
                bundle.putString("likes", likes)
                bundle.putBoolean("likedByMe", likedByMe)
                bundle.putString("url", url)
                bundle.putLong("id", id)
                findNavController().navigate(R.id.action_feedFragment_to_showPhotoFragment, bundle)
            }

            override fun onCancel() {
                viewModel.onCancel()
            }

        })

        binding.list.adapter = adapter

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swipeRefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }

        viewModel.newerCount.observe(viewLifecycleOwner) { count ->
            if (count > 0) binding.loadNewPostsButton.show()
        }

        binding.loadNewPostsButton.setOnClickListener {
            binding.loadNewPostsButton.hide()
            binding.list.smoothScrollToPosition(0)
            viewModel.read()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshPosts()
            binding.loadNewPostsButton.hide()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.fab.setOnClickListener {
            if (auth.authStateFlow.value.id != 0L) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
            } else {
                DialogManager.SignInDialog(requireContext(), object : DialogManager.Listener {
                    override fun onClick() {
                        findNavController().navigate(R.id.action_feedFragment_to_signInFragment)
                    }
                })
            }
        }

        return binding.root
    }
}


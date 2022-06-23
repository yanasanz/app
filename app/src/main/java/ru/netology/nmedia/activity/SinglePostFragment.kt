package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post

class SinglePostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentSinglePostBinding.inflate(inflater, container, false)

        val adapter = PostAdapter(object : PostInteractionListener {
            override fun onLike(post: Post) {
                viewModel.onLike(post)
            }

            override fun onShare(post: Post) {
                viewModel.onShare(post)
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
                findNavController().navigate(R.id.feedFragment)
            }

            override fun onEdit(post: Post) {
                viewModel.onEdit(post)
                val text = post.content
                findNavController().navigate(R.id.action_singlePostFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = text
                    })
            }

            override fun onCancel() {
                viewModel.onCancel()
            }

            override fun onWatch(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun onTapContent(post: Post) {
                Snackbar.make(binding.root, post.content, LENGTH_SHORT)
                    .show()
            }
        })

        binding.post.adapter = adapter

        val id = arguments?.getLong("id")

        viewModel.data.observe(this.viewLifecycleOwner) {
            val post = viewModel.data.value?.filter {
                it.id == id
            }
            adapter.submitList(post)
        }

        return binding.root
    }
}

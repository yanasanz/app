package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.editPost.observe(this) { post: Post? ->
            with(binding) {
                val content = post?.content ?: ""
                contentEditText.setText(content)
                textToEdit.text = content
                group.visibility = View.VISIBLE
            }
        }

        binding.saveButton.setOnClickListener {
            with(binding) {
                val content = contentEditText.text.toString()
                viewModel.onSaveButtonClicked(content)
                contentEditText.clearFocus()
                contentEditText.hideKeyboard()
                group.visibility = View.GONE
            }
        }

        binding.cancelButton.setOnClickListener {
            with(binding) {
                contentEditText.clearFocus()
                contentEditText.hideKeyboard()
                viewModel.onCancel()
                group.visibility = View.GONE
            }
        }
    }
}
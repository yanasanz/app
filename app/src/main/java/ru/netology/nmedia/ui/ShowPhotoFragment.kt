package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentShowPhotoBinding

@AndroidEntryPoint
class ShowPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShowPhotoBinding.inflate(inflater, container, false)

        val url = "${BuildConfig.BASE_URL}/media/${requireArguments().getString("url")}"

        Glide.with(binding.showPhoto)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_placeholder_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.showPhoto)

        return binding.root
    }
}
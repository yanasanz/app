package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentRegisterBinding
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.RegisterViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var auth: AppAuth

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.addAvatar.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_addAvatarFragment)
        }

        binding.signUpButton.setOnClickListener {
            val login = binding.login.text.toString()
            val pass = binding.password.text.toString()
            val name = binding.userName.text.toString()

            when {
                binding.login.text.isNullOrBlank() || binding.password.text.isNullOrBlank() -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.error_filling_forms),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                binding.confirmPassword.text.toString() != pass -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.password_doesnt_match),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                viewModel.avatar.value?.file == null -> {
                    viewModel.register(login, pass, name)
                }
                else -> {
                    val media = viewModel.avatar.value?.file?.let { MediaUpload(it) }
                    media?.let { viewModel.registerWithPhoto(login, pass, name, media) }
                }
            }
            viewModel.data.observe(viewLifecycleOwner) {
                auth.setAuth(it.id, it.token)
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }

        }
        return binding.root
    }
}
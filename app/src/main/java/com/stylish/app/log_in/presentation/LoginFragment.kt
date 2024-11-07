package com.stylish.app.log_in.presentation

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stylish.app.R
import com.stylish.app.core.presentation.util.MyTextWatcher
import com.stylish.app.core.presentation.util.checkConnection
import com.stylish.app.core.presentation.util.showSnackBar
import com.stylish.app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        subscribe()
    }

    private fun subscribe() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            val isLoading = it.getContentIfNotHandled()
            isLoading?.let {
                setLoadingState(isLoading)
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.emailLayout.error = null
            } else {
                binding.emailLayout.error = uiText.asString(requireContext())
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.passwordLayout.error = null
            } else {
                binding.passwordLayout.error = uiText.asString(requireContext())
            }
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            val uiText = it.getContentIfNotHandled()
            uiText?.let {
                showSnackBar(uiText.asString(requireContext()), binding.root)
            }
        }

        viewModel.openHomeScreen.observe(viewLifecycleOwner) {
            val openHomeScreen = it.getContentIfNotHandled()
            openHomeScreen?.let {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }

    private fun setupListeners() {
        binding.emailEt.addTextChangedListener(mailTextWatcher)
        binding.passwordEt.addTextChangedListener(passwordTextWatcher)

        binding.logInBtn.setOnClickListener {
            checkConnection(
                {
                    viewModel.validateCredentials(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
                },binding.root
            )
        }
    }

    private val mailTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onEmailChanged()
        }
    }

    private val passwordTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onPasswordChanged()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loaderLayout.loader.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.emailLayout.isEnabled = !isLoading
        binding.passwordLayout.isEnabled = !isLoading
        binding.logInBtn.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.emailEt.removeTextChangedListener(mailTextWatcher)
        binding.passwordEt.removeTextChangedListener(passwordTextWatcher)
        _binding = null
    }

}
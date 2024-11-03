package com.stylish.app.splash_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stylish.app.R
import com.stylish.app.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
    }

    private fun subscribe() {
        viewModel.userLoggedIn.observe(viewLifecycleOwner) { isUserLoggedIn->
            if (isUserLoggedIn) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
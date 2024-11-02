package com.stylish.app.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.stylish.app.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
    }

    private fun subscribe() {
        viewModel.userLoggedIn.observe(viewLifecycleOwner) { isUserLoggedIn->
            val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
            if (isUserLoggedIn) {
                findNavController().navigate(R.id.homeFragment, null, navOptions)
            } else {
                findNavController().navigate(R.id.loginFragment, null, navOptions)
            }
        }
    }



}
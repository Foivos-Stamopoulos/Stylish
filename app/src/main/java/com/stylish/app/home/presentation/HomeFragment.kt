package com.stylish.app.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stylish.app.R
import com.stylish.app.core.presentation.util.ObserveAsEvents
import com.stylish.app.core.presentation.util.checkConnection
import com.stylish.app.databinding.FragmentHomeBinding
import com.stylish.app.ui.theme.StylishTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                StylishTheme {

                    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    ObserveAsEvents(flow = viewModel.effect) { effect ->
                        when (effect) {
                            is HomeContract.Effect.OpenProductDetailScreen -> {
                                checkConnection(
                                    {
                                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(effect.id))
                                }, binding.root
                                )
                            }
                            is HomeContract.Effect.ShowMessage -> {
                                scope.launch {
                                    snackBarHostState.showSnackbar(effect.uiText.asString(requireContext()))
                                }
                            }
                        }
                    }

                    val state = viewModel.state.collectAsState().value

                    HomeScreen(
                        state = state,
                        event = viewModel::onEvent,
                        snackBarHostState = snackBarHostState
                    )
                }
            }
        }

        viewModel.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
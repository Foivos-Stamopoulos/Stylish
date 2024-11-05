package com.stylish.app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.repository.ProductRepository
import com.stylish.app.core.domain.util.DataError
import com.stylish.app.core.domain.util.Result
import com.stylish.app.core.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel(), HomeContract {

    private val _state = MutableStateFlow(HomeContract.State())
    override val state: StateFlow<HomeContract.State>
        get() = _state.asStateFlow()

    private var _effect = Channel<HomeContract.Effect>()
    override val effect: Flow<HomeContract.Effect>
        get() = _effect.receiveAsFlow()

    init {
        start()
    }

    private fun start() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val deferredAllCategories = async { productRepository.getAllCategories() }
            val deferredAllProducts = async { productRepository.getAllProducts() }

            val allCategoriesResult = deferredAllCategories.await()
            val allProductsResult = deferredAllProducts.await()

            onDataLoaded(allCategoriesResult, allProductsResult)
        }
    }

    private suspend fun onDataLoaded(
        allCategoriesResult: Result<List<String>, DataError.Network>,
        allProductsResult: Result<List<Product>, DataError>
    ) {
        if (allCategoriesResult is Result.Success && allProductsResult is Result.Success) {
            _state.update {
                it.copy(
                    categories = allCategoriesResult.data,
                    products = allProductsResult.data,
                    isLoading = false
                )
            }
            return
        }
        if (allCategoriesResult is Result.Error) {
            _state.update { it.copy(isLoading = false) }
            val errorUiText = allCategoriesResult.error.asUiText()
            _effect.send(HomeContract.Effect.ShowMessage(errorUiText))
        }
        else if (allProductsResult is Result.Error) {
            _state.update { it.copy(isLoading = false) }
            val errorUiText = allProductsResult.error.asUiText()
            _effect.send(HomeContract.Effect.ShowMessage(errorUiText))
        }
    }

    override fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnProductClick -> {
                openProductDetailScreen(event.id)
            }
            is HomeContract.Event.OnCategoryClick -> {
                // Do nothing for the demo purposes
            }
        }
    }

    private fun openProductDetailScreen(id: Int) {
        viewModelScope.launch {
            _effect.send(HomeContract.Effect.OpenProductDetailScreen(id))
        }
    }

}
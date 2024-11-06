package com.stylish.app.product_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylish.app.core.data.util.Event
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.repository.ProductRepository
import com.stylish.app.core.domain.util.Result
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.core.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading

    private val _snackBarMessage = MutableLiveData<Event<UiText?>>()
    val snackBarMessage: LiveData<Event<UiText?>> get() = _snackBarMessage

    private val _openEditProductScreen = MutableLiveData<Event<Product>>()
    val openEditProductScreen: LiveData<Event<Product>> get() = _openEditProductScreen

    private val productId = ProductDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).productId

    fun start() {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            when (val productResult = productRepository.getProductById(productId)) {
                is Result.Success -> {
                    _product.value = productResult.data
                }
                is Result.Error -> {
                    _snackBarMessage.value = Event(productResult.error.asUiText())
                }
            }
            _isLoading.value = Event(false)
        }
    }

    fun onEditProductClick() {
        product.value?.let { product ->
            _openEditProductScreen.value = Event(product)
        }
    }

}
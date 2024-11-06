package com.stylish.app.edit_product.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.stylish.app.core.data.util.Event
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.repository.ProductRepository
import com.stylish.app.core.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val product = EditProductFragmentArgs.fromSavedStateHandle(savedStateHandle).product

    private val _product = MutableLiveData<Product>()
    val productLD: LiveData<Product> get() = _product

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading

    private val _snackBarMessage = MutableLiveData<Event<UiText?>>()
    val snackBarMessage: LiveData<Event<UiText?>> get() = _snackBarMessage

    init {
        _product.value = product
    }

}
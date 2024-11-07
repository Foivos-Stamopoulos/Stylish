package com.stylish.app.edit_product.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylish.app.R
import com.stylish.app.core.data.util.Event
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.domain.repository.ProductRepository
import com.stylish.app.core.domain.util.Result
import com.stylish.app.core.presentation.util.UiText
import com.stylish.app.core.presentation.util.asUiText
import com.stylish.app.edit_product.domain.InputTextValidator
import com.stylish.app.edit_product.domain.PriceValidator
import com.stylish.app.edit_product.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val textInputTextValidator: InputTextValidator,
    private val priceValidator: PriceValidator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val product = EditProductFragmentArgs.fromSavedStateHandle(savedStateHandle).product

    private val _product = MutableLiveData<Product>()
    val productLD: LiveData<Product> get() = _product

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading

    private val _titleError = MutableLiveData<UiText?>()
    val titleError : LiveData<UiText?> get() = _titleError

    private val _priceError = MutableLiveData<UiText?>()
    val priceError : LiveData<UiText?> get() = _priceError

    private val _category = MutableLiveData<String>()
    val category : LiveData<String> get() = _category

    private val _categoryError = MutableLiveData<UiText?>()
    val categoryError : LiveData<UiText?> get() = _categoryError

    private val _descriptionError = MutableLiveData<UiText?>()
    val descriptionError : LiveData<UiText?> get() = _descriptionError

    private val _snackBarMessage = MutableLiveData<Event<UiText?>>()
    val snackBarMessage: LiveData<Event<UiText?>> get() = _snackBarMessage

    private val _openSelectCategoryBottomSheet = MutableLiveData<SelectCategory>()
    val openSelectCategoryBottomSheet: LiveData<SelectCategory> get() = _openSelectCategoryBottomSheet

    private lateinit var categories: List<String>

    init {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            when (val categoriesResult = productRepository.getAllCategories()) {
                is Result.Success -> {
                    categories = categoriesResult.data
                    _product.value = product
                }
                is Result.Error -> {
                    _snackBarMessage.value = Event(categoriesResult.error.asUiText())
                }
            }
            _isLoading.value = Event(false)
        }
    }

    fun onCategorySelected(category: String) {
        _category.value = category
    }

    fun onSelectCategoryPressed() {
        if (this::categories.isInitialized) {
            _openSelectCategoryBottomSheet.value = SelectCategory(
                categories = mapListToArrayList(categories),
                selectedCategory = productLD.value!!.category
            )
        }
    }

    fun validateData(
        title: String,
        price: String,
        category: String,
        description: String
    ) {
        val titleResult = textInputTextValidator(title)
        val priceResult = priceValidator(price)
        val categoryResult = textInputTextValidator(category)
        val descriptionResult = textInputTextValidator(description)

        val hasError = listOf(titleResult, priceResult, categoryResult, descriptionResult).any { it is Result.Error }
        if (hasError) {
            if (titleResult is Result.Error) {
                _titleError.value = titleResult.error.asUiText()
            }
            if (priceResult is Result.Error) {
                _priceError.value = priceResult.error.asUiText()
            }
            if (categoryResult is Result.Error) {
                _categoryError.value = categoryResult.error.asUiText()
            }
            if (descriptionResult is Result.Error) {
                _descriptionError.value = descriptionResult.error.asUiText()
            }
            return
        }

        val priceFloat = (priceResult as Result.Success).data
        submitData(title, priceFloat, category, description)
    }

    private fun submitData(title: String,
                           price: Float,
                           category: String,
                           description: String) {
        _isLoading.value = Event(true)
        viewModelScope.launch {
            val product = Product(
                id = product.id,
                title = title,
                price = price,
                category = category,
                description = description,
                image = product.image
            )
            when (val result = productRepository.updateProduct(product.id, product)) {
                is Result.Success -> {
                    _snackBarMessage.value = Event(UiText.StringResource(R.string.message_update_success))
                }
                is Result.Error -> {
                    _snackBarMessage.value = Event(result.error.asUiText())
                }
            }
            _isLoading.value = Event(false)
        }
    }

    private fun mapListToArrayList(categories: List<String>): ArrayList<String> {
        val arrayList = ArrayList<String>()
        for (element in categories) {
            arrayList.add(element)
        }
        return arrayList
    }

    fun onTitleChanged() {
        _titleError.value = null
    }

    fun onPriceChanged() {
        _priceError.value = null
    }

    fun onCategoryChanged() {
        _categoryError.value = null
    }

    fun onDescriptionChanged() {
        _descriptionError.value = null
    }

    data class SelectCategory(
        val categories: ArrayList<String>,
        val selectedCategory: String
    )

}
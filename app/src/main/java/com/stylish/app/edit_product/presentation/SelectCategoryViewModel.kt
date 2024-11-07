package com.stylish.app.edit_product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectCategoryViewModel : ViewModel(), SelectCategoryContract {

    private val _state = MutableStateFlow(SelectCategoryContract.State())
    override val state: StateFlow<SelectCategoryContract.State>
        get() = _state.asStateFlow()

    private val _effect = Channel<SelectCategoryContract.Effect>()
    override val effect: Flow<SelectCategoryContract.Effect>
        get() = _effect.receiveAsFlow()

    fun start(categories: List<String>, selectedCategory: String) {
        _state.update { it.copy(categories = categories, selectedCategory = selectedCategory) }
    }

    override fun onEvent(event: SelectCategoryContract.Event) {
        when (event) {
            is SelectCategoryContract.Event.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
                setCategory(event.category)
            }
        }
    }

    private fun setCategory(category: String) {
        viewModelScope.launch {
            _effect.send(SelectCategoryContract.Effect.SetCategory(category))
        }
    }

}
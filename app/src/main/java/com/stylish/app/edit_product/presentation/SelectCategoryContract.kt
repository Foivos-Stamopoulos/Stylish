package com.stylish.app.edit_product.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SelectCategoryContract {

    data class State(
        val categories: List<String> = emptyList(),
        val selectedCategory: String? = null
    )

    sealed class Event {
        data class OnCategorySelected(val category: String) : Event()
    }

    sealed class Effect {
        data class SetCategory(val category: String) : Effect()
    }

    val state: StateFlow<State>

    val effect: Flow<Effect>

    fun onEvent(event: Event)

}
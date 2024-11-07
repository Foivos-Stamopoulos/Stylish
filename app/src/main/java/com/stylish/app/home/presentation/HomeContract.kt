package com.stylish.app.home.presentation

import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.presentation.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HomeContract {

    data class State(
        val categories: List<String> = emptyList(),
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class OnCategoryClick(val name: String): Event
        data class OnProductClick(val id: Int): Event
    }

    sealed interface Effect {
        data class OpenProductDetailScreen(val id: Int): Effect
        data class ShowMessage(val uiText: UiText): Effect
    }

    val state: StateFlow<State>

    val effect: Flow<Effect>

    fun onEvent(event: Event)

}
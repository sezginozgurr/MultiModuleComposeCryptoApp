package com.example.multimodulecomposecryptoapp.presentation.home

import com.example.multimodulecomposecryptoapp.domain.model.Coin

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val coins: List<Coin> = emptyList(),
        val error: String? = null
    )

    sealed interface UiAction {
        data object RefreshCoins : UiAction
        data class ToggleFavorite(val coin: Coin) : UiAction
        data class NavigateToDetail(val coinId: String) : UiAction
    }

    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect
        data class NavigateToDetail(val coinId: String) : UiEffect
    }
} 
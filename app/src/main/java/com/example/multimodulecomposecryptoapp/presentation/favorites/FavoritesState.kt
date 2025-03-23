package com.example.multimodulecomposecryptoapp.presentation.favorites

import com.example.multimodulecomposecryptoapp.domain.model.Coin

data class FavoritesState(
    val coins: List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 
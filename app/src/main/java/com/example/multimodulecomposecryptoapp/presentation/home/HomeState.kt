package com.example.multimodulecomposecryptoapp.presentation.home

import com.example.multimodulecomposecryptoapp.domain.model.Coin

data class HomeState(
    val coins: List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 
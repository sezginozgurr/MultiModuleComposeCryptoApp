package com.example.multimodulecomposecryptoapp.domain.use_case

import javax.inject.Inject

data class CoinUseCases @Inject constructor(
    val getCoins: GetCoinsUseCase,
    val getCoinById: GetCoinByIdUseCase,
    val getFavoriteCoins: GetFavoriteCoinsUseCase,
    val toggleFavorite: ToggleFavoriteUseCase
) 
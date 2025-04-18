package com.example.multimodulecomposecryptoapp.domain.usecase

import javax.inject.Inject

data class CoinUseCases @Inject constructor(
    val getCoins: GetCoinsUseCase,
    val getCoinById: GetCoinByIdUseCase,
    val getFavoriteCoins: GetFavoriteCoinsUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val getCoinDetail: GetCoinDetailUseCase,
    val refreshCoins: RefreshCoinsUseCase
) 
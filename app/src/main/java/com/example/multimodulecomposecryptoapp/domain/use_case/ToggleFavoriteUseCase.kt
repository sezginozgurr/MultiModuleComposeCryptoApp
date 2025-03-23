package com.example.multimodulecomposecryptoapp.domain.use_case

import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coin: Coin) {
        if (coin.isFavorite) {
            repository.removeCoinFromFavorites(coin.id)
        } else {
            repository.addCoinToFavorites(coin)
        }
    }
} 
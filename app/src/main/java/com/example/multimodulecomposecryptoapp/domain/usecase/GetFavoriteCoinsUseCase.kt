package com.example.multimodulecomposecryptoapp.domain.usecase

import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(): Flow<List<Coin>> {
        return repository.getFavoriteCoins()
    }
} 
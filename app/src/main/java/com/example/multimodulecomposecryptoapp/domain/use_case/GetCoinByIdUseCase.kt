package com.example.multimodulecomposecryptoapp.domain.use_case

import com.example.multimodulecomposecryptoapp.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String): Resource<Coin> {
        return repository.getCoinById(coinId)
    }
} 
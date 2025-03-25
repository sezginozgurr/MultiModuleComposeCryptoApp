package com.example.multimodulecomposecryptoapp.domain.use_case

import com.example.common.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(): Resource<Flow<List<Coin>>> {
        return repository.getCoins()
    }
} 
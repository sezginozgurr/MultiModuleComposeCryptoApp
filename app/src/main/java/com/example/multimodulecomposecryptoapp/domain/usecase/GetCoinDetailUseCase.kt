package com.example.multimodulecomposecryptoapp.domain.usecase

import com.example.common.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String): Resource<CoinDetail> {
        return repository.getCoinDetail(coinId)
    }
} 
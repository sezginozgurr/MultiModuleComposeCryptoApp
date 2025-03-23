package com.example.multimodulecomposecryptoapp.domain.use_case

import com.example.multimodulecomposecryptoapp.core.common.Resource
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
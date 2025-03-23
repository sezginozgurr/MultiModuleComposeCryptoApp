package com.example.multimodulecomposecryptoapp.data.remote.dto

data class CoinsResponseDto(
    val status: String,
    val data: CoinsDataDto
)

data class CoinsDataDto(
    val stats: StatsDto,
    val coins: List<CoinDto>
)

data class StatsDto(
    val total: Int,
    val totalCoins: Int,
    val totalMarkets: Int,
    val totalExchanges: Int,
    val totalMarketCap: String,
    val total24hVolume: String
) 
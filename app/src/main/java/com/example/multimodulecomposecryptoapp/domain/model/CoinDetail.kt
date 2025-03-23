package com.example.multimodulecomposecryptoapp.domain.model

import java.math.BigDecimal
import java.util.Date

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: BigDecimal,
    val marketCap: BigDecimal,
    val volume: BigDecimal,
    val rank: Int,
    val priceChangePercentage24h: Double,
    val allTimeHighPrice: BigDecimal,
    val allTimeHighDate: Date,
    val totalSupply: BigDecimal,
    val circulatingSupply: BigDecimal,
    val listingDate: Date,
    val numberOfMarkets: Int,
    val numberOfExchanges: Int,
    val websiteUrl: String,
    val isFavorite: Boolean = false
)

data class PriceHistoryItem(
    val timestamp: Date,
    val price: BigDecimal
) 
package com.example.multimodulecomposecryptoapp.domain.model

import java.math.BigDecimal
import java.util.Date

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
    val marketCap: BigDecimal,
    val volume: BigDecimal,
    val priceChangePercentage24h: Double,
    val listingDate: Date,
    val isFavorite: Boolean = false,
    val page: Int = 1,
    val hasMore: Boolean = true
) 
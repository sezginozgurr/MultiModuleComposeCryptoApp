package com.example.multimodulecomposecryptoapp.data.remote.dto

import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class CoinDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    @SerializedName("iconUrl")
    val imageUrl: String,
    val price: String,
    val marketCap: String,
    @SerializedName("24hVolume")
    val volume: String,
    val change: String,
    val listedAt: Long
) {
    fun toCoin(): Coin {
        return Coin(
            id = uuid,
            symbol = symbol,
            name = name,
            imageUrl = imageUrl,
            price = BigDecimal(price),
            marketCap = BigDecimal(marketCap),
            volume = BigDecimal(volume),
            priceChangePercentage24h = change.toDouble(),
            listingDate = Date(listedAt * 1000L),
            isFavorite = false
        )
    }
} 
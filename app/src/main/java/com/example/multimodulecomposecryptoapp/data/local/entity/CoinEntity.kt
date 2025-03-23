package com.example.multimodulecomposecryptoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "favorites")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val marketCap: Double,
    val volume: Double,
    val priceChangePercentage24h: Double,
    val listingDate: Long
) {
    fun toCoin(): Coin {
        return Coin(
            id = id,
            symbol = symbol,
            name = name,
            imageUrl = imageUrl,
            price = BigDecimal(price),
            marketCap = BigDecimal(marketCap),
            volume = BigDecimal(volume),
            priceChangePercentage24h = priceChangePercentage24h,
            listingDate = Date(listingDate),
            isFavorite = true
        )
    }
    
    companion object {
        fun fromCoin(coin: Coin): CoinEntity {
            return CoinEntity(
                id = coin.id,
                symbol = coin.symbol,
                name = coin.name,
                imageUrl = coin.imageUrl,
                price = coin.price.toDouble(),
                marketCap = coin.marketCap.toDouble(),
                volume = coin.volume.toDouble(),
                priceChangePercentage24h = coin.priceChangePercentage24h,
                listingDate = coin.listingDate.time
            )
        }
    }
} 
package com.example.multimodulecomposecryptoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    @SerializedName("iconUrl")
    val imageUrl: String,
    val description: String?,
    val price: String,
    val marketCap: String,
    @SerializedName("24hVolume")
    val volume: String,
    val rank: Int,
    @SerializedName("change")
    val priceChangePercentage24h: String,
    @SerializedName("sparkline")
    val sparkline: List<String>,
    val allTimeHigh: AllTimeHighDto,
    val supply: SupplyDto,
    val numberOfMarkets: Int,
    val numberOfExchanges: Int,
    val listedAt: Long,
    val links: List<LinkDto>?
)

data class AllTimeHighDto(
    val price: String,
    val timestamp: Long
)

data class SupplyDto(
    val confirmed: Boolean,
    val total: String,
    val circulating: String
)

data class LinkDto(
    val name: String,
    val type: String,
    val url: String
)

data class CoinDetailResponseDto(
    val status: String,
    val data: CoinDetailDataDto
)

data class CoinDetailDataDto(
    val coin: CoinDetailDto
) 
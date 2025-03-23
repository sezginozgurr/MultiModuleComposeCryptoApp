package com.example.multimodulecomposecryptoapp.data.remote.api

import com.example.multimodulecomposecryptoapp.data.remote.dto.CoinsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinRankingApi {
    
    @GET(Endpoints.COINS)
    suspend fun getCoins(
        @Query(Endpoints.Params.LIMIT) limit: Int = Endpoints.Params.DefaultValues.DEFAULT_LIMIT,
        @Query(Endpoints.Params.OFFSET) offset: Int = Endpoints.Params.DefaultValues.DEFAULT_OFFSET,
        @Query(Endpoints.Params.ORDER_BY) orderBy: String = Endpoints.Params.DefaultValues.DEFAULT_ORDER_BY,
        @Query(Endpoints.Params.ORDER_DIRECTION) orderDirection: String = Endpoints.Params.DefaultValues.DEFAULT_ORDER_DIRECTION
    ): CoinsResponseDto
    
    @GET(Endpoints.COIN_DETAILS)
    suspend fun getCoinById(
        @Query(Endpoints.Params.UUID) coinId: String
    ): CoinsResponseDto
} 
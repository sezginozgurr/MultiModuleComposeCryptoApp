package com.example.multimodulecomposecryptoapp.domain.repository

import com.example.common.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    
    suspend fun getCoins(): Resource<Flow<List<Coin>>>
    
    suspend fun getCoinById(coinId: String): Resource<Coin>
    
    suspend fun getFavoriteCoins(): Flow<List<Coin>>
    
    suspend fun addCoinToFavorites(coin: Coin)
    
    suspend fun removeCoinFromFavorites(coinId: String)
    
    suspend fun isCoinFavorite(coinId: String): Boolean
    
    suspend fun toggleFavorite(coin: Coin)
    
    suspend fun getCoinDetail(coinId: String): Resource<CoinDetail>
} 
package com.example.multimodulecomposecryptoapp.data.repository

import com.example.common.core.common.Resource
import com.example.common.core.common.map
import com.example.common.core.network.safeApiCall
import com.example.multimodulecomposecryptoapp.data.local.dao.CoinDao
import com.example.multimodulecomposecryptoapp.data.local.entity.CoinEntity
import com.example.multimodulecomposecryptoapp.data.mapper.toCoinDetail
import com.example.multimodulecomposecryptoapp.data.remote.api.CoinRankingApi
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val api: CoinRankingApi,
    private val dao: CoinDao
) : CoinRepository {
    
    override suspend fun getCoins(page: Int, pageSize: Int): Resource<Flow<List<Coin>>> {
        return safeApiCall { 
            api.getCoins(
                limit = pageSize,
                offset = (page - 1) * pageSize
            )
        }.map { response ->
            val remoteCoins = response.data.coins.map { it.toCoin(page = page) }
            getFavoriteCoins().map { favoriteCoins ->
                remoteCoins.map { coin ->
                    coin.copy(isFavorite = favoriteCoins.any { it.id == coin.id })
                }
            }
        }
    }
    
    override suspend fun refreshCoins(page: Int, pageSize: Int): Resource<Flow<List<Coin>>> {
        return safeApiCall { 
            api.getCoins(
                limit = pageSize,
                offset = (page - 1) * pageSize
            )
        }.map { response ->
            val remoteCoins = response.data.coins.map { it.toCoin(page = page) }
            getFavoriteCoins().map { favoriteCoins ->
                remoteCoins.map { coin ->
                    coin.copy(isFavorite = favoriteCoins.any { it.id == coin.id })
                }
            }
        }
    }
    
    override suspend fun getCoinById(coinId: String): Resource<Coin> {
        return safeApiCall { 
            api.getCoinById(coinId = coinId)
        }.map { response ->
            val coin = response.data.coins.first().toCoin()
            val isFavorite = isCoinFavorite(coinId)
            coin.copy(isFavorite = isFavorite)
        }
    }
    
    override suspend fun getFavoriteCoins(): Flow<List<Coin>> {
        return dao.getFavoriteCoins().map { entities ->
            entities.map { it.toCoin() }
        }
    }
    
    override suspend fun addCoinToFavorites(coin: Coin) {
        dao.insertCoin(CoinEntity.fromCoin(coin.copy(isFavorite = true)))
    }
    
    override suspend fun removeCoinFromFavorites(coinId: String) {
        dao.deleteCoinById(coinId)
    }
    
    override suspend fun isCoinFavorite(coinId: String): Boolean {
        return dao.isCoinFavorite(coinId)
    }
    
    override suspend fun toggleFavorite(coin: Coin) {
        if (coin.isFavorite) {
            removeCoinFromFavorites(coin.id)
        } else {
            addCoinToFavorites(coin)
        }
    }
    
    override suspend fun getCoinDetail(coinId: String): Resource<CoinDetail> {
        return safeApiCall {
            api.getCoinDetail(coinId)
        }.map { response ->
            val isFavorite = isCoinFavorite(coinId)
            response.data.coin.toCoinDetail(isFavorite)
        }
    }
} 
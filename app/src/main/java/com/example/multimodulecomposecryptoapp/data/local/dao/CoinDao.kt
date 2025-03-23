package com.example.multimodulecomposecryptoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.multimodulecomposecryptoapp.data.local.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    
    @Query("SELECT * FROM favorites")
    fun getFavoriteCoins(): Flow<List<CoinEntity>>
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :coinId LIMIT 1)")
    suspend fun isCoinFavorite(coinId: String): Boolean
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinEntity)
    
    @Query("DELETE FROM favorites WHERE id = :coinId")
    suspend fun deleteCoinById(coinId: String)
} 
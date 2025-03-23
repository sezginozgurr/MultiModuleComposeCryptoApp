package com.example.multimodulecomposecryptoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.multimodulecomposecryptoapp.data.local.dao.CoinDao
import com.example.multimodulecomposecryptoapp.data.local.entity.CoinEntity

@Database(
    entities = [CoinEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDatabase : RoomDatabase() {
    
    abstract val coinDao: CoinDao
    
    companion object {
        const val DATABASE_NAME = "coin_db"
    }
} 
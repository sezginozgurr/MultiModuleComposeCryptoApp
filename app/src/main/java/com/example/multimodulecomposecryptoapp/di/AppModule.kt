package com.example.multimodulecomposecryptoapp.di

import android.app.Application
import androidx.room.Room
import com.example.multimodulecomposecryptoapp.data.local.CoinDatabase
import com.example.multimodulecomposecryptoapp.data.local.dao.CoinDao
import com.example.multimodulecomposecryptoapp.data.remote.api.CoinRankingApi
import com.example.multimodulecomposecryptoapp.data.repository.CoinRepositoryImpl
import com.example.multimodulecomposecryptoapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    private const val API_KEY = "coinranking57478e79939e861d4d077297e54cdd0b833b957f88dad1b6" //fixme It is included here because it is a challenge project.

    private const val HEADER_API_KEY = "x-access-token" //fixme It is included here because it is a challenge project.


    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header(HEADER_API_KEY, API_KEY)
                .build()
                
            chain.proceed(newRequest)
        }
    }
    
    @Provides
    @Singleton
    fun provideHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideCoinRankingApi(client: OkHttpClient): CoinRankingApi {
        return Retrofit.Builder()
            .baseUrl("https://api.coinranking.com/") //fixme It is included here because it is a challenge project.

            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CoinRankingApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideCoinDatabase(app: Application): CoinDatabase {
        return Room.databaseBuilder(
            app,
            CoinDatabase::class.java,
            CoinDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideCoinDao(db: CoinDatabase): CoinDao {
        return db.coinDao
    }
    
    @Provides
    @Singleton
    fun provideCoinRepository(
        api: CoinRankingApi,
        dao: CoinDao
    ): CoinRepository {
        return CoinRepositoryImpl(api, dao)
    }
} 
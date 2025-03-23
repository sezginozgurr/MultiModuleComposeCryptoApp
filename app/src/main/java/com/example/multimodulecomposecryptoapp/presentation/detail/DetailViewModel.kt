package com.example.multimodulecomposecryptoapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multimodulecomposecryptoapp.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import com.example.multimodulecomposecryptoapp.domain.use_case.CoinUseCases
import com.example.multimodulecomposecryptoapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state
    
    init {
        savedStateHandle.get<String>(Screen.COIN_ID_KEY)?.let { coinId ->
            loadCoinDetail(coinId)
        }
    }

    private fun loadCoinDetail(coinId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val detailResult = coinUseCases.getCoinDetail(coinId)
            
            when (detailResult) {
                is Resource.Success -> {
                    _state.update { 
                        it.copy(
                            coinDetail = detailResult.data,
                            isLoading = false,
                            error = null
                        ) 
                    }
                }
                is Resource.Error -> {
                    _state.update { 
                        it.copy(
                            error = "Coin detayları yüklenemedi: ${detailResult.exception.message}",
                            isLoading = false
                        ) 
                    }
                }
            }
        }
    }
    
    fun toggleFavorite() {
        viewModelScope.launch {
            val currentDetail = _state.value.coinDetail ?: return@launch
            
            val coin = com.example.multimodulecomposecryptoapp.domain.model.Coin(
                id = currentDetail.id,
                symbol = currentDetail.symbol,
                name = currentDetail.name,
                imageUrl = currentDetail.imageUrl,
                price = currentDetail.price,
                marketCap = currentDetail.marketCap,
                volume = currentDetail.volume,
                priceChangePercentage24h = currentDetail.priceChangePercentage24h,
                listingDate = currentDetail.listingDate,
                isFavorite = currentDetail.isFavorite
            )
            
            coinUseCases.toggleFavorite(coin)
            
            _state.update {
                it.copy(
                    coinDetail = currentDetail.copy(isFavorite = !currentDetail.isFavorite)
                ) 
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null,
    val error: String? = null
) 
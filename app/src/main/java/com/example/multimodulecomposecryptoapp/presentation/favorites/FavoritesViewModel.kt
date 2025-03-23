package com.example.multimodulecomposecryptoapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
) : ViewModel() {
    
    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()
    
    init {
        getFavoriteCoins()
    }
    
    fun getFavoriteCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                coinUseCases.getFavoriteCoins().collect { coins ->
                    _state.update { it.copy(coins = coins, isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
    
    fun toggleFavorite(coin: Coin) {
        viewModelScope.launch {
            coinUseCases.toggleFavorite(coin)
        }
    }
} 
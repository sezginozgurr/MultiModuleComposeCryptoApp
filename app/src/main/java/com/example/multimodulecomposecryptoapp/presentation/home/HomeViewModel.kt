package com.example.multimodulecomposecryptoapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multimodulecomposecryptoapp.core.common.fold
import com.example.multimodulecomposecryptoapp.core.ui.mvi.MVI
import com.example.multimodulecomposecryptoapp.core.ui.mvi.mvi
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.use_case.CoinUseCases
import com.example.multimodulecomposecryptoapp.presentation.home.HomeContract.UiAction
import com.example.multimodulecomposecryptoapp.presentation.home.HomeContract.UiEffect
import com.example.multimodulecomposecryptoapp.presentation.home.HomeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {
    
    init {
        getCoins()
    }
    
    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.RefreshCoins -> getCoins()
                is UiAction.ToggleFavorite -> toggleFavorite(uiAction.coin)
                is UiAction.NavigateToDetail -> emitUiEffect(UiEffect.NavigateToDetail(uiAction.coinId))
            }
        }
    }
    
    private fun getCoins() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            coinUseCases.getCoins().fold(
                onSuccess = { flowCoins ->
                    flowCoins.collect { coins ->
                        updateUiState { copy(coins = coins, isLoading = false) }
                    }
                },
                onError = { exception ->
                    updateUiState { copy(isLoading = false, error = exception.message) }
                    emitUiEffect(UiEffect.ShowError(exception.message ?: "An unknown error occurred"))
                }
            )
        }
    }
    
    private fun toggleFavorite(coin: Coin) {
        viewModelScope.launch {
            coinUseCases.toggleFavorite(coin)
            // State otomatik olarak repository akışı üzerinden güncellenecek
        }
    }
} 
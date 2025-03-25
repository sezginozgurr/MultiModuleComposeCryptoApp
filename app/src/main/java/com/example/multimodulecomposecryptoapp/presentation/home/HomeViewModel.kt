package com.example.multimodulecomposecryptoapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.core.common.fold
import com.example.common.core.ui.mvi.MVI
import com.example.common.core.ui.mvi.mvi
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
                UiAction.RefreshCoins -> refreshCoins()
                UiAction.LoadMoreCoins -> loadMoreCoins()
                is UiAction.ToggleFavorite -> toggleFavorite(uiAction.coin)
                is UiAction.NavigateToDetail -> emitUiEffect(UiEffect.NavigateToDetail(uiAction.coinId))
            }
        }
    }
    
    private fun getCoins() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            coinUseCases.getCoins(page = 1).fold(
                onSuccess = { flowCoins ->
                    flowCoins.collect { coins ->
                        updateUiState { 
                            copy(
                                coins = coins,
                                isLoading = false,
                                currentPage = 1,
                                hasMore = coins.isNotEmpty()
                            )
                        }
                    }
                },
                onError = { exception ->
                    updateUiState { copy(isLoading = false, error = exception.message) }
                    emitUiEffect(UiEffect.ShowError(exception.message ?: "An unknown error occurred"))
                }
            )
        }
    }
    
    private fun loadMoreCoins() {
        val currentState = uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) return
        
        viewModelScope.launch {
            updateUiState { copy(isLoadingMore = true) }
            coinUseCases.getCoins(page = currentState.currentPage + 1).fold(
                onSuccess = { flowCoins ->
                    flowCoins.collect { newCoins ->
                        updateUiState { 
                            copy(
                                coins = coins + newCoins,
                                isLoadingMore = false,
                                currentPage = currentPage + 1,
                                hasMore = newCoins.isNotEmpty()
                            )
                        }
                    }
                },
                onError = { exception ->
                    updateUiState { copy(isLoadingMore = false, error = exception.message) }
                    emitUiEffect(UiEffect.ShowError(exception.message ?: "An unknown error occurred"))
                }
            )
        }
    }
    
    private fun refreshCoins() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            coinUseCases.refreshCoins(page = 1).fold(
                onSuccess = { flowCoins ->
                    flowCoins.collect { coins ->
                        updateUiState { 
                            copy(
                                coins = coins,
                                isLoading = false,
                                currentPage = 1,
                                hasMore = coins.isNotEmpty()
                            )
                        }
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
        }
    }
} 
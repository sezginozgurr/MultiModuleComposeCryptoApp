package com.example.multimodulecomposecryptoapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.core.common.Resource
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import com.example.multimodulecomposecryptoapp.domain.usecase.CoinUseCases
import com.example.multimodulecomposecryptoapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
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

            val result = coinUseCases.getCoinDetail(coinId)

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(coinDetail = result.data, isLoading = false)
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = "Coin detayları yüklenemedi: ${result.exception.message}",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val current = _state.value.coinDetail ?: return@launch

            val coin = Coin(
                id = current.id,
                symbol = current.symbol,
                name = current.name,
                imageUrl = current.imageUrl,
                price = current.price,
                marketCap = current.marketCap,
                volume = current.volume,
                priceChangePercentage24h = current.priceChangePercentage24h,
                listingDate = current.listingDate,
                isFavorite = current.isFavorite
            )

            coinUseCases.toggleFavorite(coin)

            _state.update {
                it.copy(coinDetail = current.copy(isFavorite = !current.isFavorite))
            }
        }
    }

    fun calculatePriceChange(price: BigDecimal, percent: Double): BigDecimal {
        return price.multiply(BigDecimal(percent / 100)).abs()
    }

    fun formatLargePrice(price: BigDecimal): String {
        return when {
            price >= BigDecimal(10000) -> {
                val integer = price.toInt()
                val decimal = (price - BigDecimal(integer)).multiply(BigDecimal(100)).toInt()
                "%,d.%02d".format(Locale.US, integer, decimal)
            }

            price >= BigDecimal(1) -> "%.2f".format(price)
            else -> "%.4f".format(price)
        }
    }

    fun formatPrice(price: BigDecimal): String = "%.2f".format(price)

    fun formatChangePercent(change: Double): String = "%.2f".format(change)

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale("tr"))
        return formatter.format(date)
    }

    fun formatLargeNumber(number: BigDecimal): String {
        return when {
            number >= BigDecimal(1_000_000_000) -> "${number.divide(BigDecimal(1_000_000_000)).setScale(2, RoundingMode.HALF_UP)} Milyar"
            number >= BigDecimal(1_000_000) -> "${number.divide(BigDecimal(1_000_000)).setScale(2, RoundingMode.HALF_UP)} Milyon"
            number >= BigDecimal(1_000) -> "${number.divide(BigDecimal(1_000)).setScale(2, RoundingMode.HALF_UP)} Bin"
            else -> number.toString()
        }
    }

}

data class DetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null,
    val error: String? = null
)

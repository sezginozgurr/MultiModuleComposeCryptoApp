package com.example.multimodulecomposecryptoapp.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.multimodulecomposecryptoapp.R
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val coinDetail = state.coinDetail

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = coinDetail?.symbol ?: "",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = coinDetail?.name ?: "",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.action_back)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                imageVector = if (coinDetail?.isFavorite == true)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.action_add_favorite),
                                tint = if (coinDetail?.isFavorite == true)
                                    Color.Red
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = { /* Bildirim işlemi */ }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = stringResource(R.string.action_notifications)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                coinDetail?.let { detail ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        CurrentPriceCard(
                            currentPrice = detail.price,
                            priceChangePercent = detail.priceChangePercentage24h,
                            priceChangeAmount = viewModel.calculatePriceChange(
                                detail.price,
                                detail.priceChangePercentage24h
                            ),
                            highPrice = detail.price.multiply(BigDecimal("1.002")),
                            lowPrice = detail.price.multiply(BigDecimal("0.995")),
                            formatLargePrice = viewModel::formatLargePrice,
                            formatPrice = viewModel::formatPrice,
                            formatChangePercent = viewModel::formatChangePercent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoCard(title = stringResource(R.string.screen_detail_title)) {
                            InfoRow(label = stringResource(R.string.coin_info_name), value = detail.name)
                            InfoRow(label = stringResource(R.string.coin_info_symbol), value = detail.symbol)
                            InfoRow(label = stringResource(R.string.coin_info_rank), value = "#${detail.rank}")
                            InfoRow(label = stringResource(R.string.coin_info_listing_date), value = viewModel.formatDate(detail.listingDate))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoCard(title = stringResource(R.string.coin_stats)) {
                            InfoRow(label = stringResource(R.string.coin_market_cap), value = "₺${viewModel.formatLargeNumber(detail.marketCap)}")
                            InfoRow(label = stringResource(R.string.coin_24h_volume), value = "₺${viewModel.formatLargeNumber(detail.volume)}")
                            InfoRow(label = stringResource(R.string.coin_total_supply), value = viewModel.formatLargeNumber(detail.totalSupply))
                            InfoRow(label = stringResource(R.string.coin_circulating_supply), value = viewModel.formatLargeNumber(detail.circulatingSupply))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoCard(title = stringResource(R.string.coin_all_time_high)) {
                            InfoRow(label = stringResource(R.string.coin_price), value = "₺${viewModel.formatLargePrice(detail.allTimeHighPrice)}")
                            InfoRow(label = stringResource(R.string.coin_date), value = viewModel.formatDate(detail.allTimeHighDate))
                        }

                        if (detail.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            InfoCard(title = stringResource(R.string.coin_description)) {
                                Text(
                                    text = detail.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentPriceCard(
    currentPrice: BigDecimal,
    priceChangePercent: Double,
    priceChangeAmount: BigDecimal,
    highPrice: BigDecimal,
    lowPrice: BigDecimal,
    formatLargePrice: (BigDecimal) -> String,
    formatPrice: (BigDecimal) -> String,
    formatChangePercent: (Double) -> String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "GÜNCEL FİYAT",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "₺${formatLargePrice(currentPrice)}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    val changeColor = if (priceChangePercent >= 0) Color.Green else Color.Red
                    val changeSign = if (priceChangePercent >= 0) "+" else ""

                    Text(
                        text = "$changeSign${formatChangePercent(priceChangePercent)}% ($changeSign₺${formatPrice(priceChangeAmount)})",
                        color = changeColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Yüksek: ",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "₺${formatPrice(highPrice)}",
                            color = Color.Green,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Düşük: ",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "₺${formatPrice(lowPrice)}",
                            color = Color.Red,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            content()
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    }
}




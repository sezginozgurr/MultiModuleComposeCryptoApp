package com.example.multimodulecomposecryptoapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.multimodulecomposecryptoapp.R
import com.example.multimodulecomposecryptoapp.presentation.components.PullToRefreshBox
import com.example.multimodulecomposecryptoapp.presentation.home.HomeContract.UiAction
import com.example.multimodulecomposecryptoapp.presentation.home.components.CoinItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCoinClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeContract.UiEffect.ShowError -> {
                    //snackbarHostState.showSnackbar(effect.message)
                }

                is HomeContract.UiEffect.NavigateToDetail -> {
                    onCoinClick(effect.coinId)
                }
            }
        }
    }

    LaunchedEffect(listState) {
        if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == uiState.coins.size - 1 && !uiState.isLoadingMore) {
            viewModel.onAction(UiAction.LoadMoreCoins)
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: stringResource(R.string.error_unknown),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                uiState.coins.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.error_coin_list_empty),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                else -> {
                    PullToRefreshBox(
                        isRefreshing = uiState.isLoading,
                        onRefresh = { viewModel.onAction(UiAction.RefreshCoins) }
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.screen_home_ranking_title),
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 22.sp
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )
                                        
                                        FilterChip(
                                            selected = true,
                                            onClick = { },
                                            label = { Text(stringResource(R.string.screen_home_24h_volume)) }
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }

                            LazyColumn(
                                state = listState,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = uiState.coins,
                                    key = { coin -> coin.id }
                                ) { coin ->
                                    CoinItem(
                                        coin = coin,
                                        onCoinClick = { viewModel.onAction(UiAction.NavigateToDetail(coin.id)) },
                                        onFavoriteClick = { viewModel.onAction(UiAction.ToggleFavorite(coin)) }
                                    )
                                }
                                
                                if (uiState.isLoadingMore) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        )   {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 
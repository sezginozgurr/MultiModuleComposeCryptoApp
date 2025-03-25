package com.example.multimodulecomposecryptoapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.multimodulecomposecryptoapp.domain.model.Coin
import java.math.BigDecimal
import androidx.compose.ui.layout.ContentScale

@Composable
fun CoinItem(
    coin: Coin,
    onCoinClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCoinClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = coin.imageUrl,
                    contentDescription = coin.name,
                    modifier = Modifier.circularImage(44.dp),
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${formatPrice(coin.price)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                val changeColor = if (coin.priceChangePercentage24h >= 0) Color.Green else Color.Red
                val changeSign = if (coin.priceChangePercentage24h >= 0) "+" else ""
                val changeValue = coin.priceChangePercentage24h
                
                Text(
                    text = "$changeSign${changeValue}% (${changeSign}$${calculateDollarChange(coin.price, coin.priceChangePercentage24h)})",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = changeColor,
                        fontSize = 12.sp
                    )
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (coin.isFavorite) Icons.Default.Star else Icons.Default.Star,
                    contentDescription = if (coin.isFavorite) "Favorilerden çıkar" else "Favorilere ekle",
                    tint = if (coin.isFavorite) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private fun formatPrice(price: BigDecimal): String {
    return when {
        price >= BigDecimal(1000) -> "%.2f".format(price)
        price >= BigDecimal(1) -> "%.2f".format(price)
        else -> "%.4f".format(price)
    }
}

private fun calculateDollarChange(price: BigDecimal, percentChange: Double): String {
    val changeAmount = price.multiply(BigDecimal(percentChange / 100))
    return "%.3f".format(changeAmount.abs())
} 
package com.example.multimodulecomposecryptoapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        title = "Anasayfa",
        icon = Icons.Default.Home
    )
    
    object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        title = "Favoriler",
        icon = Icons.Default.Favorite
    )
} 
package com.example.multimodulecomposecryptoapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Favorites : Screen("favorites_screen")
    object Detail : Screen("detail_screen/{coinId}") {
        fun createRoute(coinId: String) = "detail_screen/$coinId"
    }
    
    companion object {
        const val COIN_ID_KEY = "coinId"
    }
} 
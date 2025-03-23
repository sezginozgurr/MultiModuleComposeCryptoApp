package com.example.multimodulecomposecryptoapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.multimodulecomposecryptoapp.presentation.detail.DetailScreen
import com.example.multimodulecomposecryptoapp.presentation.favorites.FavoritesScreen
import com.example.multimodulecomposecryptoapp.presentation.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            // Eğer detay ekranında değilsek alt navigasyonu göster
            if (currentRoute != null && !currentRoute.startsWith(Screen.Detail.route.substringBefore("/"))) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Home.route
            ) {
                HomeScreen(
                    onCoinClick = { coinId ->
                        navController.navigate(Screen.Detail.createRoute(coinId))
                    }
                )
            }
            
            composable(
                route = Screen.Favorites.route
            ) {
                FavoritesScreen(
                    onCoinClick = { coinId ->
                        navController.navigate(Screen.Detail.createRoute(coinId))
                    }
                )
            }
            
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument(Screen.COIN_ID_KEY) {
                        type = NavType.StringType
                    }
                )
            ) {
                DetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
} 
package com.example.multimodulecomposecryptoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = TextPrimary,
    secondary = TextSecondary,
    background = DarkBackground,
    surface = CardBackground,
    onPrimary = TextPrimary,
    onSecondary = TextSecondary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val LightColorScheme = darkColorScheme(  // Sadece koyu tema kullanacağız
    primary = TextPrimary,
    secondary = TextSecondary,
    background = DarkBackground,
    surface = CardBackground,
    onPrimary = TextPrimary,
    onSecondary = TextSecondary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun MultiModuleComposeCryptoAppTheme(
    darkTheme: Boolean = true, // Her zaman koyu tema
    dynamicColor: Boolean = false, // Dynamic color'ı devre dışı bırakıyoruz
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Her zaman koyu tema kullanıyoruz

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
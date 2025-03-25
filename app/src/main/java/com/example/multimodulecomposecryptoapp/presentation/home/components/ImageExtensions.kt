package com.example.multimodulecomposecryptoapp.presentation.home.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

fun Modifier.circularImage(
    size: Dp,
    shape: Shape = CircleShape
): Modifier = this
    .size(size)
    .aspectRatio(1f, matchHeightConstraintsFirst = true)
    .clip(shape)
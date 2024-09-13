package ru.vb.practice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Spacing(
    val default: Dp = 8.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)

@Composable
fun ProvideSpacing(content1: ProvidedValue<Spacing>, content: @Composable () -> Unit) {
    val density = LocalDensity.current.density
    val spacing = Spacing(
        default = (8.dp.value * density).dp,
        extraSmall = (4.dp.value * density).dp,
        small = (8.dp.value * density).dp,
        medium = (16.dp.value * density).dp,
        large = (32.dp.value * density).dp,
        extraLarge = (64.dp.value * density).dp
    )

    androidx.compose.runtime.CompositionLocalProvider(LocalSpacing provides spacing) {
        content()
    }
}

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
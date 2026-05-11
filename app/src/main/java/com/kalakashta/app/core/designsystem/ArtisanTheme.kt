package com.kalakashta.app.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ArtisanPalette(
    val canvas: Color = Ebony,
    val surface: Color = Mahogany,
    val card: Color = CardNoir,
    val cardRaised: Color = CardRaised,
    val overlay: Color = Veil,
    val spark: Color = Spark,
    val sparkBright: Color = SparkBright,
    val sparkMuted: Color = SparkMuted,
    val sparkGhost: Color = SparkGhost,
    val honey: Color = Honey,
    val honeyLight: Color = HoneyLight,
    val ember: Color = Ember,
    val moss: Color = Moss,
    val marigold: Color = Marigold,
    val inkWhite: Color = InkWhite,
    val inkSilver: Color = InkSilver,
    val inkAsh: Color = InkAsh,
    val hairline: Color = Hairline,
    val ghostBase: Color = GhostBase,
    val ghostShine: Color = GhostShine,
    val walnut: Color = Walnut,
    val driftwood: Color = Driftwood,
    val sandal: Color = Sandal,
    val hickory: Color = Hickory,
)

val LocalArtisanPalette = staticCompositionLocalOf { ArtisanPalette() }

private val DarkScheme = darkColorScheme(
    primary = Spark,
    onPrimary = Ebony,
    primaryContainer = SparkMuted,
    onPrimaryContainer = SparkBright,
    secondary = Honey,
    onSecondary = Ebony,
    secondaryContainer = HoneyDark,
    onSecondaryContainer = HoneyLight,
    tertiary = Ember,
    onTertiary = Ebony,
    background = Ebony,
    onBackground = InkWhite,
    surface = Mahogany,
    onSurface = InkWhite,
    surfaceVariant = Walnut,
    onSurfaceVariant = InkSilver,
    outline = Hickory,
    outlineVariant = Hairline,
    error = Ember,
    onError = Ebony,
)

@Composable
fun ArtisanTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalArtisanPalette provides ArtisanPalette()) {
        MaterialTheme(
            colorScheme = DarkScheme,
            typography = ArtisanTypography,
            content = content
        )
    }
}

object Artisan {
    val palette: ArtisanPalette
        @Composable get() = LocalArtisanPalette.current
}

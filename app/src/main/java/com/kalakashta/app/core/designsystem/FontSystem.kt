package com.kalakashta.app.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system serif + sans-serif for zero-dependency reliability.
// Swap with downloaded .ttf in res/font/ for production polish.
val HeadingFamily = FontFamily.Serif      // Maps to Noto Serif on most devices
val BodyFamily = FontFamily.SansSerif     // Maps to Roboto / Inter on most devices

val ArtisanTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = HeadingFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        letterSpacing = (-1).sp,
        color = InkWhite
    ),
    displayMedium = TextStyle(
        fontFamily = HeadingFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.5).sp,
        color = InkWhite
    ),
    displaySmall = TextStyle(
        fontFamily = HeadingFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 34.sp,
        color = InkWhite
    ),
    headlineLarge = TextStyle(
        fontFamily = HeadingFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        color = InkWhite
    ),
    headlineMedium = TextStyle(
        fontFamily = HeadingFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = InkWhite
    ),
    headlineSmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = InkWhite
    ),
    titleLarge = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        color = InkWhite
    ),
    titleMedium = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = InkWhite
    ),
    titleSmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = InkSilver
    ),
    bodyLarge = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = InkWhite
    ),
    bodyMedium = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = InkSilver
    ),
    bodySmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = InkAsh
    ),
    labelLarge = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = InkWhite
    ),
    labelMedium = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = InkSilver
    ),
    labelSmall = TextStyle(
        fontFamily = BodyFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = InkAsh
    ),
)

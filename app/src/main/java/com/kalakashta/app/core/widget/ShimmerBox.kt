package com.kalakashta.app.core.widget

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kalakashta.app.core.designsystem.Artisan

/**
 * Animated shimmer placeholder — used while Unsplash images resolve.
 * Creates a diagonal light sweep over a dark base.
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp
) {
    val colors = Artisan.palette
    val transition = rememberInfiniteTransition(label = "shimmer")
    val offset by transition.animateFloat(
        initialValue = -400f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_sweep"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            colors.ghostBase,
            colors.ghostShine,
            colors.ghostBase,
        ),
        start = Offset(offset, offset * 0.6f),
        end = Offset(offset + 400f, (offset + 400f) * 0.6f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush)
    )
}

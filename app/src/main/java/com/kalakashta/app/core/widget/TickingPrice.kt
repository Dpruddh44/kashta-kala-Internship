package com.kalakashta.app.core.widget

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.kalakashta.app.core.designsystem.Artisan

/**
 * Animated counter that ticks up from 0 to [targetValue].
 * Used for the "Total Price" reveal in the Ledger screen.
 */
@Composable
fun TickingPrice(
    targetValue: Double,
    prefix: String = "₹",
    modifier: Modifier = Modifier,
    color: Color = Artisan.palette.spark,
) {
    val animatedValue by animateFloatAsState(
        targetValue = targetValue.toFloat(),
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "price_tick"
    )

    Text(
        text = "$prefix ${"%,.0f".format(animatedValue.toDouble())}",
        style = MaterialTheme.typography.displayMedium,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier
    )
}

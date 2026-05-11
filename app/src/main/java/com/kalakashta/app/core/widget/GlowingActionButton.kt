package com.kalakashta.app.core.widget

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kalakashta.app.core.designsystem.Artisan

/**
 * Massive tactile button with animated glow border when [enabled].
 * Scales down on press with spring physics.
 */
@Composable
fun GlowingActionButton(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    glowColor: Color = Artisan.palette.spark,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "btn_scale"
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (enabled) 0.7f else 0f,
        animationSpec = tween(500),
        label = "glow_alpha"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "glow_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val effectiveAlpha = if (enabled) glowAlpha * pulseAlpha else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(scale)
            .drawBehind {
                if (effectiveAlpha > 0f) {
                    drawRoundRect(
                        brush = Brush.horizontalGradient(
                            listOf(
                                glowColor.copy(alpha = effectiveAlpha * 0.5f),
                                glowColor.copy(alpha = effectiveAlpha),
                                glowColor.copy(alpha = effectiveAlpha * 0.5f),
                            )
                        ),
                        cornerRadius = CornerRadius(20f, 20f),
                        style = Stroke(width = 3f)
                    )
                }
            }
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (enabled) Brush.horizontalGradient(
                    listOf(glowColor, glowColor.copy(alpha = 0.8f))
                ) else Brush.horizontalGradient(
                    listOf(
                        Artisan.palette.hickory,
                        Artisan.palette.sandal
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = if (enabled) Artisan.palette.canvas else Artisan.palette.inkAsh
        )
    }
}

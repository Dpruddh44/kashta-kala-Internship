package com.kalakashta.app.core.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kalakashta.app.core.designsystem.Artisan

/**
 * Polaroid-style card — white-bordered frame with subtle shadow.
 * Used in the Craftbook (portfolio) screen.
 */
@Composable
fun PolaroidFrame(
    modifier: Modifier = Modifier,
    borderColor: Color = Artisan.palette.driftwood,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(6.dp),
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(6.dp))
            .background(borderColor)
            .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 28.dp),
        content = content
    )
}

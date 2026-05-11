package com.kalakashta.app.core.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kalakashta.app.core.designsystem.Artisan

/**
 * Inset "measurement gauge" style input field.
 * Dark recessed look with a unit suffix label (cm, ft, etc.).
 */
@Composable
fun GaugeInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    unit: String,
    modifier: Modifier = Modifier,
) {
    val colors = Artisan.palette
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colors.inkSilver
        )
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colors.canvas)
                .border(
                    width = 1.dp,
                    color = if (value.isNotBlank()) colors.spark.copy(alpha = 0.4f)
                    else colors.hairline,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { newVal ->
                        // Only allow numeric + decimal
                        if (newVal.isEmpty() || newVal.matches(Regex("^\\d*\\.?\\d*$"))) {
                            onValueChange(newVal)
                        }
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = colors.inkWhite,
                        textAlign = TextAlign.Start
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(colors.spark),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = colors.inkAsh
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.spark
                )
            }
        }
    }
}

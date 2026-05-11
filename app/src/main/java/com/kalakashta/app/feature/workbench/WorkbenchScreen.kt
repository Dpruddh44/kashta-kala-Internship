package com.kalakashta.app.feature.workbench

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalakashta.app.core.designsystem.Artisan
import com.kalakashta.app.core.designsystem.ArtisanTheme
import com.kalakashta.app.core.widget.GaugeInput
import com.kalakashta.app.core.widget.GlowingActionButton
import com.kalakashta.app.core.widget.TickingPrice
import com.kalakashta.app.data.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkbenchScreen(
    state: WorkbenchState,
    onLengthChange: (String) -> Unit,
    onWidthChange: (String) -> Unit,
    onThicknessChange: (String) -> Unit,
    onTimberPick: (Timber) -> Unit,
    onToggleTimberSheet: () -> Unit,
    onCompute: () -> Unit,
    canCompute: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = Artisan.palette
    val sheetState = rememberModalBottomSheetState()

    // Timber picker bottom sheet
    if (state.showTimberSheet) {
        ModalBottomSheet(
            onDismissRequest = onToggleTimberSheet,
            sheetState = sheetState,
            containerColor = colors.surface,
            contentColor = colors.inkWhite,
            dragHandle = { BottomSheetDefaults.DragHandle(color = colors.hairline) }
        ) {
            Text(
                "Select Wood Type",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            Spacer(Modifier.height(8.dp))
            Timber.entries.forEach { timber ->
                val selected = timber == state.selectedTimber
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTimberPick(timber) }
                        .padding(horizontal = 24.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(timber.tint)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            timber.label,
                            style = MaterialTheme.typography.titleMedium,
                            color = if (selected) colors.spark else colors.inkWhite
                        )
                        Text(
                            "₹${timber.ratePerSqFt.toInt()}/sq.ft — ${timber.hint}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.inkAsh
                        )
                    }
                    if (selected) {
                        Icon(Icons.Rounded.Check, "Selected", tint = colors.spark)
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.canvas)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            "Workbench",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = colors.inkWhite
        )
        Text(
            "Material area & cost estimator",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.inkAsh
        )

        Spacer(Modifier.height(32.dp))

        // ── Wood Type Selector ──
        Text("MATERIAL", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
        Spacer(Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = colors.card,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleTimberSheet() }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(state.selectedTimber.tint)
                )
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        state.selectedTimber.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.inkWhite
                    )
                    Text(
                        "₹${state.selectedTimber.ratePerSqFt.toInt()}/sq.ft",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.spark
                    )
                }
                Icon(
                    Icons.Rounded.ExpandMore,
                    "Change",
                    tint = colors.inkSilver
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        // ── Dimension Inputs ──
        Text("DIMENSIONS", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GaugeInput(
                value = state.length,
                onValueChange = onLengthChange,
                label = "Length",
                unit = "cm",
                modifier = Modifier.weight(1f)
            )
            GaugeInput(
                value = state.width,
                onValueChange = onWidthChange,
                label = "Width",
                unit = "cm",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        GaugeInput(
            value = state.thickness,
            onValueChange = onThicknessChange,
            label = "Thickness (optional)",
            unit = "mm",
            modifier = Modifier.fillMaxWidth(0.48f)
        )

        Spacer(Modifier.height(36.dp))

        // ── Calculate Button ──
        GlowingActionButton(
            label = "⚡  CALCULATE AREA",
            enabled = canCompute,
            onClick = onCompute,
        )

        // ── Result ──
        AnimatedVisibility(
            visible = state.hasResult,
            enter = fadeIn() + expandVertically(),
        ) {
            Column(modifier = Modifier.padding(top = 32.dp)) {
                HorizontalDivider(color = colors.hairline)
                Spacer(Modifier.height(24.dp))
                Text("RESULT", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
                Spacer(Modifier.height(12.dp))

                ResultRow("Area", "${"%.2f".format(state.computedSqFt)} sq.ft")
                ResultRow("Rate", "₹${state.computedSqFt.toInt()}/sq.ft")
                Spacer(Modifier.height(16.dp))

                Text("Estimated Material Cost",
                    style = MaterialTheme.typography.bodySmall, color = colors.inkAsh)
                TickingPrice(targetValue = state.computedCost)
            }
        }

        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    val colors = Artisan.palette
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = colors.inkSilver)
        Text(value, style = MaterialTheme.typography.titleMedium, color = colors.inkWhite)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WorkbenchPreview() {
    ArtisanTheme {
        WorkbenchScreen(
            state = WorkbenchState(
                length = "120",
                width = "60",
                thickness = "18",
                selectedTimber = Timber.TEAK,
                computedSqFt = 7.75,
                computedCost = 2712.0,
                hasResult = true
            ),
            onLengthChange = {}, onWidthChange = {}, onThicknessChange = {},
            onTimberPick = {}, onToggleTimberSheet = {}, onCompute = {},
            canCompute = true
        )
    }
}

package com.kalakashta.app.feature.ledger

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
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
import com.kalakashta.app.core.widget.TickingPrice

@Composable
fun LedgerScreen(
    state: LedgerState,
    onItemLabel: (String) -> Unit,
    onClientName: (String) -> Unit,
    onLabourRate: (String) -> Unit,
    onMaterialCost: (String) -> Unit,
    onMarginChange: (Float) -> Unit,
    onRecalc: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = Artisan.palette

    Box(modifier = modifier.fillMaxSize().background(colors.canvas)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(bottom = 80.dp)
        ) {
            Text(
                "Ledger",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = colors.inkWhite
            )
            Text(
                "Build your price quote",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.inkAsh
            )

            Spacer(Modifier.height(28.dp))

            // ── Carry-over from Workbench ──
            if (state.carryOverTimber.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = colors.sparkGhost,
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                "From Workbench",
                                style = MaterialTheme.typography.labelMedium,
                                color = colors.spark
                            )
                            Text(
                                "${state.carryOverTimber} • ${"%.1f".format(state.carryOverSqFt)} sq.ft",
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.inkWhite
                            )
                        }
                        Text(
                            "₹${"%.0f".format(state.materialCost)}",
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.spark,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            // ── Client & Item Info ──
            Text("JOB DETAILS", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
            Spacer(Modifier.height(10.dp))

            GaugeInput(
                value = state.clientName,
                onValueChange = onClientName,
                label = "Client Name",
                unit = "",
            )
            Spacer(Modifier.height(14.dp))

            GaugeInput(
                value = state.itemLabel,
                onValueChange = onItemLabel,
                label = "Item / Description",
                unit = "",
            )

            Spacer(Modifier.height(28.dp))

            // ── Cost Breakdown ──
            Text("COST BREAKDOWN", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                GaugeInput(
                    value = state.materialCost.let { if (it == 0.0) "" else it.toInt().toString() },
                    onValueChange = onMaterialCost,
                    label = "Material Cost",
                    unit = "₹",
                    modifier = Modifier.weight(1f)
                )
                GaugeInput(
                    value = state.labourRate,
                    onValueChange = {
                        onLabourRate(it)
                        onRecalc()
                    },
                    label = "Labour Cost",
                    unit = "₹",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(28.dp))

            // ── Profit Margin Slider ──
            Text("PROFIT MARGIN", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = state.marginPercent,
                    onValueChange = {
                        onMarginChange(it)
                        onRecalc()
                    },
                    valueRange = 0f..50f,
                    steps = 9,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = colors.spark,
                        activeTrackColor = colors.spark,
                        inactiveTrackColor = colors.hairline,
                    )
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "${state.marginPercent.toInt()}%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.honey,
                )
            }

            Spacer(Modifier.height(32.dp))
            HorizontalDivider(color = colors.hairline)
            Spacer(Modifier.height(24.dp))

            // ── Grand Total ──
            Text("GRAND TOTAL", style = MaterialTheme.typography.labelMedium, color = colors.inkAsh)
            Spacer(Modifier.height(8.dp))
            TickingPrice(targetValue = state.grandTotal)

            // ── Saved Confirmation ──
            AnimatedVisibility(visible = state.saved) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = colors.moss.copy(alpha = 0.15f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        "✓ Invoice saved to local database",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.moss,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // ── Save FAB ──
        FloatingActionButton(
            onClick = onSave,
            containerColor = colors.spark,
            contentColor = colors.canvas,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Rounded.Save, "Save Invoice")
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LedgerPreview() {
    ArtisanTheme {
        LedgerScreen(
            state = LedgerState(
                clientName = "Rajesh Kumar",
                itemLabel = "TV Unit – Teak",
                materialCost = 2712.0,
                labourRate = "1500",
                marginPercent = 20f,
                grandTotal = 5054.0,
                carryOverTimber = "Teak (सागौन)",
                carryOverSqFt = 7.75,
            ),
            onItemLabel = {}, onClientName = {}, onLabourRate = {},
            onMaterialCost = {}, onMarginChange = {}, onRecalc = {}, onSave = {},
        )
    }
}

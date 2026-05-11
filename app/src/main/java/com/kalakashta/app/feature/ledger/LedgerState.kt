package com.kalakashta.app.feature.ledger

data class LedgerState(
    val itemLabel: String = "",
    val clientName: String = "",
    val materialCost: Double = 0.0,
    val labourRate: String = "",
    val marginPercent: Float = 15f,
    val grandTotal: Double = 0.0,
    val saved: Boolean = false,
    val carryOverTimber: String = "",
    val carryOverSqFt: Double = 0.0,
)

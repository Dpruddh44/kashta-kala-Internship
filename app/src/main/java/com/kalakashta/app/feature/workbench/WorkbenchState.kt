package com.kalakashta.app.feature.workbench

import com.kalakashta.app.data.Timber

data class WorkbenchState(
    val length: String = "",
    val width: String = "",
    val thickness: String = "",
    val selectedTimber: Timber = Timber.PLY,
    val showTimberSheet: Boolean = false,
    val computedSqFt: Double = 0.0,
    val computedCost: Double = 0.0,
    val hasResult: Boolean = false,
)

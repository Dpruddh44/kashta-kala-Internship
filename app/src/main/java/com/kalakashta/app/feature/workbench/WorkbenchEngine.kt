package com.kalakashta.app.feature.workbench

import androidx.lifecycle.ViewModel
import com.kalakashta.app.data.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkbenchEngine : ViewModel() {

    private val _state = MutableStateFlow(WorkbenchState())
    val state = _state.asStateFlow()

    fun setLength(v: String) = _state.update { it.copy(length = v, hasResult = false) }
    fun setWidth(v: String) = _state.update { it.copy(width = v, hasResult = false) }
    fun setThickness(v: String) = _state.update { it.copy(thickness = v, hasResult = false) }

    fun pickTimber(t: Timber) = _state.update {
        it.copy(selectedTimber = t, showTimberSheet = false, hasResult = false)
    }

    fun toggleTimberSheet() = _state.update { it.copy(showTimberSheet = !it.showTimberSheet) }

    fun compute() {
        val s = _state.value
        val l = s.length.toDoubleOrNull() ?: return
        val w = s.width.toDoubleOrNull() ?: return
        // Convert cm to feet: 1 ft = 30.48 cm
        val sqFt = (l * w) / 929.03  // cm² → ft²
        val cost = sqFt * s.selectedTimber.ratePerSqFt
        _state.update { it.copy(computedSqFt = sqFt, computedCost = cost, hasResult = true) }
    }

    val canCompute: Boolean
        get() = _state.value.run {
            length.toDoubleOrNull() != null && width.toDoubleOrNull() != null
        }
}

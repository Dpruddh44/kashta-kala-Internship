package com.kalakashta.app.feature.ledger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kalakashta.app.persist.ArtisanVault
import com.kalakashta.app.persist.InvoiceRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LedgerEngine(app: Application) : AndroidViewModel(app) {

    private val dao = ArtisanVault.open(app).invoices()
    private val _state = MutableStateFlow(LedgerState())
    val state = _state.asStateFlow()

    fun setItemLabel(v: String) = _state.update { it.copy(itemLabel = v) }
    fun setClientName(v: String) = _state.update { it.copy(clientName = v) }
    fun setLabourRate(v: String) = _state.update { it.copy(labourRate = v) }
    fun setMargin(v: Float) = _state.update { it.copy(marginPercent = v) }

    /** Accept material cost pushed from the Workbench screen. */
    fun acceptWorkbenchData(timber: String, sqFt: Double, cost: Double) {
        _state.update {
            it.copy(carryOverTimber = timber, carryOverSqFt = sqFt, materialCost = cost)
        }
        recalculate()
    }

    fun setMaterialCostManual(v: String) {
        _state.update { it.copy(materialCost = v.toDoubleOrNull() ?: 0.0) }
        recalculate()
    }

    fun recalculate() {
        val s = _state.value
        val labour = s.labourRate.toDoubleOrNull() ?: 0.0
        val subtotal = s.materialCost + labour
        val margin = subtotal * (s.marginPercent / 100.0)
        _state.update { it.copy(grandTotal = subtotal + margin) }
    }

    fun saveInvoice() {
        val s = _state.value
        viewModelScope.launch {
            dao.save(
                InvoiceRecord(
                    clientName = s.clientName.ifBlank { "Walk-in" },
                    itemLabel = s.itemLabel.ifBlank { "Custom Furniture" },
                    timber = s.carryOverTimber,
                    spec = "${"%.1f".format(s.carryOverSqFt)} sq.ft",
                    sqft = s.carryOverSqFt,
                    materialTotal = s.materialCost,
                    labourTotal = s.labourRate.toDoubleOrNull() ?: 0.0,
                    margin = s.marginPercent.toDouble(),
                    grandTotal = s.grandTotal,
                )
            )
            _state.update { it.copy(saved = true) }
        }
    }

    fun resetSaved() = _state.update { it.copy(saved = false) }
}

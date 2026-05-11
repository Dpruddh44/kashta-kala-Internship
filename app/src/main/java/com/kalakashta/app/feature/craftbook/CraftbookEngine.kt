package com.kalakashta.app.feature.craftbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kalakashta.app.persist.ArtisanVault
import com.kalakashta.app.persist.CraftRecord
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CraftbookEngine(app: Application) : AndroidViewModel(app) {

    private val dao = ArtisanVault.open(app).crafts()
    private val _uiState = MutableStateFlow(CraftbookState())

    val state: StateFlow<CraftbookState> = combine(
        dao.stream(),
        _uiState
    ) { records, ui ->
        ui.copy(photos = records)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CraftbookState())

    fun toggleAddDialog() = _uiState.update { it.copy(showAddDialog = !it.showAddDialog) }
    fun setCaption(v: String) = _uiState.update { it.copy(newCaption = v) }

    fun addPhoto(uri: String) {
        viewModelScope.launch {
            dao.save(CraftRecord(photoUri = uri, caption = _uiState.value.newCaption.ifBlank { "My Work" }))
            _uiState.update { it.copy(showAddDialog = false, newCaption = "") }
        }
    }

    fun removePhoto(record: CraftRecord) {
        viewModelScope.launch { dao.remove(record) }
    }
}

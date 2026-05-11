package com.kalakashta.app.feature.craftbook

import com.kalakashta.app.persist.CraftRecord

data class CraftbookState(
    val photos: List<CraftRecord> = emptyList(),
    val showAddDialog: Boolean = false,
    val newCaption: String = "",
)

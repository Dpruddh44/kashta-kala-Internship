package com.kalakashta.app.feature.inspiration

import com.kalakashta.app.data.FurnitureFamily
import com.kalakashta.app.data.InspirationItem

data class InspirationState(
    val items: List<InspirationItem> = emptyList(),
    val activeFamily: FurnitureFamily = FurnitureFamily.ALL,
    val searchQuery: String = "",
    val loading: Boolean = true,
    val error: String? = null,
    val heartedIds: Set<String> = emptySet(),
)

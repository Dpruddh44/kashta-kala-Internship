package com.kalakashta.app.feature.inspiration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalakashta.app.BuildConfig
import com.kalakashta.app.data.FurnitureFamily
import com.kalakashta.app.data.InspirationItem
import com.kalakashta.app.remote.HttpHub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InspirationEngine : ViewModel() {

    private val _state = MutableStateFlow(InspirationState())
    val state = _state.asStateFlow()

    init { loadPhotos(FurnitureFamily.ALL) }

    fun pickFamily(family: FurnitureFamily) {
        _state.update { it.copy(activeFamily = family) }
        loadPhotos(family)
    }

    fun updateSearch(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun submitSearch() {
        val q = _state.value.searchQuery.ifBlank { _state.value.activeFamily.searchTag }
        loadPhotos(query = q)
    }

    fun toggleHeart(uid: String) {
        _state.update { current ->
            val newHearts = current.heartedIds.toMutableSet()
            if (uid in newHearts) newHearts.remove(uid) else newHearts.add(uid)
            val updatedItems = current.items.map { item ->
                if (item.uid == uid) item.copy(hearted = uid in newHearts) else item
            }
            current.copy(heartedIds = newHearts, items = updatedItems)
        }
    }

    private fun loadPhotos(family: FurnitureFamily? = null, query: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            try {
                val q = query ?: family?.searchTag ?: "modern furniture"
                val key = BuildConfig.UNSPLASH_KEY

                if (key.isBlank()) {
                    // Fallback: use Unsplash source URLs for demo
                    _state.update { it.copy(items = generateDemoItems(q), loading = false) }
                    return@launch
                }

                val result = HttpHub.photoFeed.search(
                    query = q,
                    auth = "Client-ID $key"
                )
                val mapped = result.hits.map { hit ->
                    InspirationItem(
                        uid = hit.uid,
                        title = hit.altCaption ?: hit.caption ?: "Furniture Design",
                        family = family ?: FurnitureFamily.ALL,
                        fullUrl = hit.urls.regular,
                        thumbUrl = hit.urls.small,
                        artist = hit.photographer.name,
                        hearted = hit.uid in _state.value.heartedIds,
                        ratio = hit.height.toFloat() / hit.width.toFloat()
                    )
                }
                _state.update { it.copy(items = mapped, loading = false) }
            } catch (e: Exception) {
                // On network failure, show demo data
                _state.update {
                    it.copy(
                        items = generateDemoItems(query ?: "furniture"),
                        loading = false,
                        error = "Offline mode — showing samples"
                    )
                }
            }
        }
    }

    private fun generateDemoItems(query: String): List<InspirationItem> {
        val tags = listOf(
            "modern sofa", "wooden bed frame", "oak cabinet",
            "dining table wood", "designer chair", "bookshelf walnut",
            "teak wardrobe", "coffee table marble", "kitchen island wood",
            "living room furniture", "minimalist desk", "rustic bench"
        )
        return tags.mapIndexed { idx, tag ->
            InspirationItem(
                uid = "demo_$idx",
                title = tag.replaceFirstChar { it.uppercase() },
                family = FurnitureFamily.ALL,
                fullUrl = "https://source.unsplash.com/featured/800x${(900 + idx * 50)}/?$tag",
                thumbUrl = "https://source.unsplash.com/featured/400x${(450 + idx * 25)}/?$tag",
                artist = "Kala-Kashta",
                ratio = listOf(1.0f, 1.3f, 0.9f, 1.5f, 1.1f, 1.4f)[idx % 6]
            )
        }
    }
}

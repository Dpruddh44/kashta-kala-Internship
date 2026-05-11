package com.kalakashta.app.data

import androidx.compose.ui.graphics.Color
import com.kalakashta.app.core.designsystem.*

/** Furniture families for the filter chips in the Inspiration screen. */
enum class FurnitureFamily(val label: String, val searchTag: String) {
    ALL("All", "modern furniture interior"),
    SOFA("Sofas", "modern sofa design"),
    BED("Beds", "modern bed bedroom"),
    CABINET("Cabinets", "wooden cabinet"),
    TABLE("Tables", "wooden table dining"),
    CHAIR("Chairs", "designer chair"),
    SHELF("Shelves", "bookshelf modern");
}

/** Wood/material types with INR pricing for the Workbench estimator. */
enum class Timber(
    val label: String,
    val ratePerSqFt: Double,
    val tint: Color,
    val hint: String
) {
    TEAK("Teak (सागौन)", 350.0, Honey, "Premium hardwood, highly durable"),
    SHEESHAM("Sheesham (शीशम)", 280.0, Ember, "Rich grain, excellent for carving"),
    PLY("Plywood (प्लाइवुड)", 85.0, Spark, "Engineered, cost-effective"),
    MDF("MDF (एमडीएफ)", 55.0, Moss, "Smooth finish for painted surfaces"),
    PINE("Pine (चीड़)", 120.0, Marigold, "Lightweight, rustic character");
}

/** A single design card loaded from Unsplash. */
data class InspirationItem(
    val uid: String,
    val title: String,
    val family: FurnitureFamily,
    val fullUrl: String,
    val thumbUrl: String,
    val artist: String = "",
    val hearted: Boolean = false,
    val ratio: Float = 1.2f // height-to-width for staggered sizing
)

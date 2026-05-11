package com.kalakashta.app.remote

import com.google.gson.annotations.SerializedName

/** Unsplash /search/photos JSON response mapping. */
data class PhotoSearchResult(
    @SerializedName("results") val hits: List<PhotoHit>
)

data class PhotoHit(
    @SerializedName("id") val uid: String,
    @SerializedName("description") val caption: String?,
    @SerializedName("alt_description") val altCaption: String?,
    @SerializedName("urls") val urls: PhotoUrls,
    @SerializedName("user") val photographer: Photographer,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
)

data class PhotoUrls(
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String,
)

data class Photographer(
    @SerializedName("name") val name: String,
)

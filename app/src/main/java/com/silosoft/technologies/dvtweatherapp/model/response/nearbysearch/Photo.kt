package com.silosoft.technologies.dvtweatherapp.model.response.nearbysearch

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val height: Int,
    @Json(name = "html_attributions")
    val htmlAttributions: List<String>,
    @Json(name = "photo_reference")
    val photoReference: String,
    val width: Int
)

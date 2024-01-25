package com.silosoft.technologies.dvtweatherapp.data.response.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Snow(
    @Json(name = "3h")
    val h: Double
)

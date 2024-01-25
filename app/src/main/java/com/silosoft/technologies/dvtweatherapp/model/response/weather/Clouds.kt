package com.silosoft.technologies.dvtweatherapp.model.response.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Int
)

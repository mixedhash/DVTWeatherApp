package com.silosoft.technologies.dvtweatherapp.model.response.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Int
)
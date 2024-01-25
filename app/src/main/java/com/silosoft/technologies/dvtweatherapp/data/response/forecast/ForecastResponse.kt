package com.silosoft.technologies.dvtweatherapp.data.response.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Day>,
    val message: Int
)

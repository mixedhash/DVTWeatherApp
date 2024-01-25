package com.silosoft.technologies.dvtweatherapp.data.response.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Day(
    val clouds: Clouds,
    val dt: Int,
    @Json(name = "dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val snow: Snow?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

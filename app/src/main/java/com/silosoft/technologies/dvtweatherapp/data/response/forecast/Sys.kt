package com.silosoft.technologies.dvtweatherapp.data.response.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    val pod: String
)

package com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NearbySearchResponse(
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any>,
    @Json(name = "next_page_token")
    val nextPageToken: String,
    val results: List<Result>,
    val status: String
)

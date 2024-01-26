package com.silosoft.technologies.dvtweatherapp.data.response_model.nearbysearch

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "business_status")
    val businessStatus: String,
    val geometry: Geometry,
    val icon: String,
    @Json(name = "icon_background_color")
    val iconBackgroundColor: String,
    @Json(name = "icon_mask_base_uri")
    val iconMaskBaseUri: String,
    val name: String,
    @Json(name = "opening_hours")
    val openingHours: OpeningHours?,
    @Json(name = "permanently_closed")
    val permanentlyClosed: Boolean?,
    val photos: List<Photo>,
    @Json(name = "place_id")
    val placeId: String,
    @Json(name = "plus_code")
    val plusCode: PlusCode,
    @Json(name = "price_level")
    val priceLevel: Int,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    @Json(name = "user_ratings_total")
    val userRatingsTotal: Int,
    val vicinity: String
)

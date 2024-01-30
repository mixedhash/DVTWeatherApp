package com.silosoft.technologies.dvtweatherapp.domain.model

data class NearbyRestaurantsUiModel(
    val restaurants: List<Restaurant>
)

data class Restaurant(
    val name: String,
    val overallRating: Double,
    val userRatings: Int,
    val vicinity: String
)

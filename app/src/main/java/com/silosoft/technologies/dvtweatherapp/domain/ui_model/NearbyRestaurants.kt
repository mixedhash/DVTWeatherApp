package com.silosoft.technologies.dvtweatherapp.domain.ui_model

data class NearbyRestaurants(
    val restaurants: List<Restaurant>
)

data class Restaurant(
    val name: String,
    val ratings: Int,
    val url: String
)

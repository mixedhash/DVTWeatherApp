package com.silosoft.technologies.dvtweatherapp.domain.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.domain.model.NearbyRestaurantsUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.Restaurant
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import timber.log.Timber
import javax.inject.Inject

class GetNearbyRestaurantsUseCase @Inject constructor(
    private val nearbySearchRepository: NearbySearchRepository
) {
    suspend fun execute(location: String): NearbyRestaurantsUiModel? {
        val result = nearbySearchRepository.getNearbyRestaurants(location)
        val nearbyRestaurants = mutableListOf<Restaurant>()
        return when (result) {
            is Result.Success -> {
                result.data.results.forEach { restaurant ->
                    nearbyRestaurants.add(
                        Restaurant(
                            name = restaurant.name,
                            overallRating = restaurant.rating,
                            userRatings = restaurant.userRatingsTotal,
                            vicinity = restaurant.vicinity
                        )
                    )
                }

                NearbyRestaurantsUiModel(restaurants = nearbyRestaurants)
            }
            is Result.Error -> {
                Timber.e(result.error)
                null
            }
        }
    }
}

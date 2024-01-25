package com.silosoft.technologies.dvtweatherapp.presentation.nav

import com.silosoft.technologies.dvtweatherapp.Constants.FAVOURITE_BOTTOM_TAB_ROUTE
import com.silosoft.technologies.dvtweatherapp.Constants.HOME_BOTTOM_TAB_ROUTE
import com.silosoft.technologies.dvtweatherapp.Constants.MAP_BOTTOM_TAB_ROUTE

enum class Screen(val route: String) {
    MainScreen(HOME_BOTTOM_TAB_ROUTE),
    FavouriteScreen(FAVOURITE_BOTTOM_TAB_ROUTE),
    MapScreen(MAP_BOTTOM_TAB_ROUTE)
}
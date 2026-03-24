package hu.bme.aut.android.weatherinfo.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {
    @Serializable
    data object CityListScreenDestination : Screen

    @Serializable
    data class WeatherScreenDestination(val cityName: String) : Screen
}

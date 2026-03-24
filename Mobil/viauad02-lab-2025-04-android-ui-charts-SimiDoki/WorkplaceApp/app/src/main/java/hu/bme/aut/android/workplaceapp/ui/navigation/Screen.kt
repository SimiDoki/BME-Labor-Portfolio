package hu.bme.aut.android.workplaceapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface Screen : NavKey {
    @Serializable
    data object MenuScreenDestination: Screen
    @Serializable
    data object ProfileScreenDestination: Screen
    @Serializable
    data object HolidayScreenDestination: Screen
}

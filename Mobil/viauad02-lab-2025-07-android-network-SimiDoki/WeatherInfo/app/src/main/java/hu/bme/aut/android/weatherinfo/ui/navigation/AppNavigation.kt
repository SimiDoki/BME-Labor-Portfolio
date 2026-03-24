package hu.bme.aut.android.weatherinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import hu.bme.aut.android.weatherinfo.ui.screen.citylist.CityListScreen
import hu.bme.aut.android.weatherinfo.ui.screen.weather.WeatherScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Screen.CityListScreenDestination)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.CityListScreenDestination> {
                CityListScreen(
                    modifier = modifier,
                    onCityClick = { backStack.add(Screen.WeatherScreenDestination(cityName = it)) }
                )
            }

            entry<Screen.WeatherScreenDestination> { key ->
                WeatherScreen(
                    modifier = modifier,
                    cityName = key.cityName,
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}

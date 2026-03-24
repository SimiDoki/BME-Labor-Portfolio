package hu.bme.aut.android.workplaceapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import hu.bme.aut.android.workplaceapp.ui.screen.holiday.HolidayScreen
import hu.bme.aut.android.workplaceapp.ui.screen.menu.MenuScreen
import hu.bme.aut.android.workplaceapp.ui.screen.profile.ProfileScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Screen>(Screen.MenuScreenDestination) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.MenuScreenDestination> {
                MenuScreen(
                    onProfileButtonClick = {
                        backStack.add(Screen.ProfileScreenDestination)
                    },
                    onSalaryButtonClick = {},
                    onHolidayButtonClick = {
                        backStack.add(Screen.HolidayScreenDestination)
                    },
                    onCafeteriaButtonClick = {}
                )
            }

            entry<Screen.ProfileScreenDestination> {
                ProfileScreen()
            }

            entry<Screen.HolidayScreenDestination> {
                HolidayScreen()
            }
        }
    )
}

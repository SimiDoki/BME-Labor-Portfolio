package hu.bme.aut.android.publictransport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import hu.bme.aut.android.publictransport.screen.ListScreen
import hu.bme.aut.android.publictransport.screen.LoginScreen
import hu.bme.aut.android.publictransport.screen.DetailsScreen
import hu.bme.aut.android.publictransport.screen.PassScreen

data object LoginScreenDestination
data object ListScreenDestination
data class DetailsScreenDestination(val type: String)
data class PassScreenDestination(val details: String)

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(LoginScreenDestination) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is LoginScreenDestination -> NavEntry(key) {
                    LoginScreen(onSuccess = {
                        backStack.add(ListScreenDestination)
                    })
                }

                is ListScreenDestination -> NavEntry(key) {
                    ListScreen(onTransportClick = { backStack.add(DetailsScreenDestination(it)) })
                }

                is DetailsScreenDestination -> NavEntry(key) {
                    DetailsScreen(
                        onSuccess = { backStack.add(PassScreenDestination(it)) },
                        transportType = key.type
                    )
                }

                is PassScreenDestination -> NavEntry(key) {
                    PassScreen(passDetails = key.details)
                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}







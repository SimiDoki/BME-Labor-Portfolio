package hu.bme.aut.android.workplaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import hu.bme.aut.android.workplaceapp.ui.navigation.AppNavigation
import hu.bme.aut.android.workplaceapp.ui.theme.WorkplaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkplaceAppTheme {
                AppNavigation()
            }
        }
    }
}

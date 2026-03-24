package hu.bme.aut.android.simpledrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import hu.bme.aut.android.simpledrawer.ui.screen.DrawingScreen
import hu.bme.aut.android.simpledrawer.ui.theme.SimpleDrawerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleDrawerTheme {
                DrawingScreen()
            }
        }
    }
}

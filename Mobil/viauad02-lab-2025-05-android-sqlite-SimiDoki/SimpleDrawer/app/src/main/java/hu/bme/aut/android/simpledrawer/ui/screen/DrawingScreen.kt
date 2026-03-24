package hu.bme.aut.android.simpledrawer.ui.screen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.simpledrawer.ui.common.BottomBar
import hu.bme.aut.android.simpledrawer.ui.common.DrawingCanvas
import hu.bme.aut.android.simpledrawer.ui.common.TopBar

@Composable
fun DrawingScreen(modifier: Modifier = Modifier,
                  viewModel: DrawingViewModel = viewModel(
                      factory = ViewModelProvider.AndroidViewModelFactory(
                          LocalContext.current.applicationContext as Application
                      )
                  )
) {
    //HT8QHM
    val drawingMode by viewModel.drawingMode.collectAsState()
    val drawElements by viewModel.drawElements.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(viewModel = viewModel)
        }
    ) { innerPadding ->
        DrawingCanvas(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            drawingMode = drawingMode,
            viewModel = viewModel,
            drawElements = drawElements
        )
    }
}

@Composable
@Preview
fun PreviewDrawingScreen() {
    DrawingScreen()
}

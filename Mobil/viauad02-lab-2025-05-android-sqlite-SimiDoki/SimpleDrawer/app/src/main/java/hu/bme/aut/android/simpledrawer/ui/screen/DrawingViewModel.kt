package hu.bme.aut.android.simpledrawer.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point
import hu.bme.aut.android.simpledrawer.sqlite.PersistentDataHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrawingViewModel(application: Application) : AndroidViewModel(application) {

    private val _drawingMode = MutableStateFlow(DrawingMode.LINE)
    val drawingMode: StateFlow<DrawingMode> = _drawingMode

    private val _drawElements = MutableStateFlow<List<Any>>(emptyList())
    val drawElements: StateFlow<List<Any>> = _drawElements

    private val dataHelper = PersistentDataHelper(application)

    init{
        loadDrawElements()
    }

    fun setDrawingMode(mode: DrawingMode) {
        viewModelScope.launch {
            _drawingMode.value = mode
        }
    }

    fun addDrawElement(element: Any) {
        viewModelScope.launch {
            _drawElements.value += element
            saveDrawElements()
        }
    }

    private fun saveDrawElements() {
        viewModelScope.launch {
            dataHelper.open()
            dataHelper.clearPoints()
            dataHelper.clearLines()
            val points = _drawElements.value.filterIsInstance<Point>()
            val lines = _drawElements.value.filterIsInstance<Line>()
            dataHelper.persistPoints(points)
            dataHelper.persistLines(lines)
            dataHelper.close()
        }
    }

    private fun loadDrawElements() {
        viewModelScope.launch {
            dataHelper.open()
            val points = dataHelper.restorePoints()
            val lines = dataHelper.restoreLines()
            _drawElements.value = points + lines
            dataHelper.close()
        }
    }

    //HT8QHM
    fun clearCanvas() {
        viewModelScope.launch {
            _drawElements.value = emptyList()
            dataHelper.open()
            dataHelper.clearPoints()
            dataHelper.clearLines()
            dataHelper.close()
        }
    }

}

enum class DrawingMode {
    LINE,
    POINT
}

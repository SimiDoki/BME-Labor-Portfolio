package hu.bme.aut.android.simpledrawer.ui.common

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point
import hu.bme.aut.android.simpledrawer.ui.screen.DrawingMode
import hu.bme.aut.android.simpledrawer.ui.screen.DrawingViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    currentColor: Color = Color.Red,
    drawingMode: DrawingMode,
    viewModel: DrawingViewModel,
    drawElements: List<Any>
) {
    var startPoint by remember { mutableStateOf<Offset?>(null) }
    var endPoint by remember { mutableStateOf<Offset?>(null) }
    var tempPoint by remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = modifier
            .background(Color.Black)
            .pointerInteropFilter { event ->
                when (event.action) {
                    /*TODO*/
                    //HT8QHM
                    //ACTION_DOWN
                    MotionEvent.ACTION_DOWN -> {
                        startPoint = Offset(event.x, event.y)
                        tempPoint = startPoint
                    }

                    //ACTION_MOVE
                    MotionEvent.ACTION_MOVE -> {
                        tempPoint = Offset(event.x, event.y)
                        if (drawingMode == DrawingMode.LINE) {
                            endPoint = tempPoint
                        }
                    }

                    //ACTION_UP
                    MotionEvent.ACTION_UP -> {
                        if (drawingMode == DrawingMode.POINT) {
                            tempPoint?.let {
                                viewModel.addDrawElement(Point(it.x, it.y, currentColor))
                            }
                        } else if (drawingMode == DrawingMode.LINE) {
                            endPoint?.let {
                                startPoint?.let { start ->
                                    viewModel.addDrawElement(
                                        Line(
                                            Point(start.x, start.y, currentColor),
                                            Point(it.x, it.y, currentColor),
                                            currentColor
                                        )
                                    )
                                }
                            }

                        }
                        startPoint = null
                        endPoint = null
                        tempPoint = null
                    }

                }
                true
            }
    ) {

        tempPoint?.let {
            if (drawingMode == DrawingMode.POINT) {
                drawCircle(color = currentColor, center = it, radius = 5f)
            } else if (drawingMode == DrawingMode.LINE && startPoint != null) {
                drawLine(color = currentColor, start = startPoint!!, end = it, strokeWidth = 5f)
            }
        }

        drawElements.forEach { element ->
            when (element) {
                is Point -> drawCircle(
                    color = element.color,
                    center = Offset(element.x, element.y),
                    radius = 5f
                )

                is Line -> drawLine(
                    color = element.color,
                    start = Offset(element.start.x, element.start.y),
                    end = Offset(element.end.x, element.end.y),
                    strokeWidth = 5f
                )
            }
        }

    }
}

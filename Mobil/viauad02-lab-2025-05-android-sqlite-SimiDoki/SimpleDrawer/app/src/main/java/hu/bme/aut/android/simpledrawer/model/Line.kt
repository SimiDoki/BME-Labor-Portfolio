package hu.bme.aut.android.simpledrawer.model

import androidx.compose.ui.graphics.Color

data class Line(
    var start: Point,
    var end: Point,
    var color: Color = Color.Yellow
)

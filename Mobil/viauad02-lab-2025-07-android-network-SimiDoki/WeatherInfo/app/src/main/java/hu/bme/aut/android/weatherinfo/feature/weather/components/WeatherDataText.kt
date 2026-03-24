package hu.bme.aut.android.weatherinfo.feature.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun WeatherDataText(
    modifier: Modifier = Modifier,
    label: String,
    value: String?,
    textSize: TextUnit = TextUnit.Unspecified
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = textSize
        )
        Text(
            text = value ?: "",
            fontSize = textSize
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WeatherDataTextPreview() {
    WeatherDataText(
        label = "Temperature",
        value = "20",
        textSize = 16.sp
    )
}

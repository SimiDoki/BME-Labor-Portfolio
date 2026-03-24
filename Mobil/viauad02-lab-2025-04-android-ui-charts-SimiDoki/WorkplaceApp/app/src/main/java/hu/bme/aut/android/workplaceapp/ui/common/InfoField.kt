package hu.bme.aut.android.workplaceapp.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoField(title: String, value: String) {
    Column(
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text(
            color = Color.Gray,
            text = title,
            fontSize = 20.sp
        )
        Text(
            text = value,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun PreviewInfoField() {
    InfoField(title = "Name", value = "Test User")
}

package hu.bme.aut.android.workplaceapp.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.workplaceapp.R

@Composable
fun ImageButton(
    modifier: Modifier,
    label: String,
    painter: Painter,
    size: Dp,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        border = BorderStroke(1.dp, darkColorScheme().onPrimary)

    ) {
        Box(modifier = modifier.padding(0.dp)) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .align(Alignment.Center)
                    .size(size),
                painter = painter,
                contentDescription = contentDescription
            )
            Text(
                color = Color.Black,
                modifier = modifier.align(Alignment.Center),
                text = label
            )

        }
    }
}

@Composable
@Preview
fun PreviewImageButton() {
    ImageButton(
        modifier = Modifier,
        label = "Holiday",
        painter = painterResource(id = R.drawable.holiday),
        size = 160.dp,
        contentDescription = "Holiday",
        onClick = {}
    ) 
}

package hu.bme.aut.android.publictransport.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import android.app.DatePickerDialog
import java.util.Calendar
import java.util.Locale
import hu.bme.aut.android.publictransport.R
//HT8QHM
@Composable
fun PassScreen(
    passDetails: String
) {

    val parts = passDetails.split(";")

    val startDate = parts[0]
    val endDate = parts[1]
    val category = parts[2]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "$category Pass",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "$startDate - $endDate",
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )

        }
        Image(
            painter = painterResource(
                id = R.drawable.qrcode
            ),
            contentDescription = "Ticket",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
@Preview
fun PreviewPassScreen() {
    PassScreen(passDetails = "2024. 09. 01.;2024. 12. 08.;Senior Train")
}

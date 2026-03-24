package hu.bme.aut.android.publictransport.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.DatePickerDialog
import java.util.Calendar
import java.util.Locale

//HT8QHM
@Composable
fun DetailsScreen(
    onSuccess: (s: String) -> Unit,
    transportType: String
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var startDate by remember {
        mutableStateOf(
            String.format(
                Locale.US,
                "%d. %02d. %02d",
                year,
                month + 1,
                day
            )
        )
    }
    var endDate by remember {
        mutableStateOf(
            String.format(
                Locale.US,
                "%d. %02d. %02d",
                year,
                month + 1,
                day
            )
        )
    }
    val currentDate = "$year. ${month + 1}. $day"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //Pass category
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = "${transportType} pass",
            fontSize = 24.sp
        )

        //Start date
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Start date",
            fontSize = 16.sp
        )
        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        startDate = String.format(
                            Locale.US,
                            "%d. %02d. %02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                        )
                    },
                    year, month, day
                ).show()
            }) {
            Text(
                text = if (startDate.isEmpty()) currentDate else startDate,
                fontSize = 24.sp
            )
        }


        //End date
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "End date",
            fontSize = 16.sp
        )

        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        endDate = String.format(
                            Locale.US,
                            "%d. %02d. %02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                        )
                    },
                    year, month, day
                ).show()
            }) {
            Text(
                text = if (endDate.isEmpty()) currentDate else endDate,
                fontSize = 24.sp
            )
        }


        //Price category
        val categories = listOf("Full price", "Senior", "Public servant")
        var selectedCategory by remember { mutableStateOf("Full price") }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Price category",
            fontSize = 16.sp
        )
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            categories.forEach { category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (category == selectedCategory),
                            onClick = { selectedCategory = category },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (category == selectedCategory),
                        onClick = { selectedCategory = category }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(category)
                }
            }
        }


        //Price
        Text(
            fontSize = 24.sp,
            text = "Price: 42000",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
        )


        //Buy button
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                onSuccess("${startDate};$endDate;${"$selectedCategory $transportType"}")
            }) {
            Text("Buy")
        }

    }
}

@Preview
@Composable
fun PreviewDetailsScreen() {
    DetailsScreen(onSuccess = {}, transportType = "Senior Bus Pass")
}

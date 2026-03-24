package hu.bme.aut.android.workplaceapp.ui.screen.holiday

import android.R.attr.enabled
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import hu.bme.aut.android.workplaceapp.R
import hu.bme.aut.android.workplaceapp.ui.common.TopBar
import java.util.Calendar

@Composable
fun HolidayScreen(
    modifier: Modifier = Modifier,
    viewModel: HolidayViewModel = viewModel()
) {
    val maxHolidayValue by viewModel.maxHolidayValue.collectAsState()
    val takenHolidayValue by viewModel.takenHolidayValue.collectAsState()
    val remainingHolidays = maxHolidayValue - takenHolidayValue

    val currentDate = Calendar.getInstance()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopBar("Holiday")
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //PieChartData létrehozása
            //...
            //HT8QHM
            val pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice("Remaining", remainingHolidays.toFloat(), Color(0xFFFFEB3B)),
                    PieChartData.Slice("Taken", takenHolidayValue.toFloat(), Color(0xFF00FF00)),
                ), plotType = PlotType.Pie
            )

            //PieChartConfig létrehozása
            //...
            val pieChartConfig = PieChartConfig(
                backgroundColor = Color.Transparent,
                labelType = PieChartConfig.LabelType.VALUE,
                isAnimationEnable = true,
                labelVisible = true,
                sliceLabelTextSize = TextUnit(20f, TextUnitType.Sp),
                animationDuration = 1000,
                sliceLabelTextColor = Color.Black,
                inActiveSliceAlpha = .8f,
                activeSliceAlpha = 1.0f,
            )

            //PieChart létrehozása - PieChartData, PieChartConfig segítségével
            //...
            //HT8QHM
            PieChart(
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp),
                pieChartData,
                pieChartConfig
            )


            //Holiday Button
            //...
            Button(
                onClick = { showDialog = true }
            ) {
                Text("Take holiday")
            }

            //DatePicker Dialog
            //...
            //DatePicker Dialog
            //HT8QHM
            if (showDialog) {

                val today = currentDate.timeInMillis
                val maxDateMillis = today + remainingHolidays * 24L * 60L * 60L * 1000L

                val datePickerState = rememberDatePickerState(
                    selectableDates = object : androidx.compose.material3.SelectableDates {
                        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                            return utcTimeMillis in today..maxDateMillis
                        }
                    }
                )

                //val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        //HT8QHM
                        TextButton(
                            onClick = {
                                showDialog = false
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    val currentMillis = currentDate.timeInMillis
                                    if (selectedMillis >= currentMillis) {
                                        val diff =
                                            ((datePickerState.selectedDateMillis!! - currentDate.timeInMillis) / (24 * 60 * 60 * 1000)).toInt() + 1
                                        viewModel.takeHoliday(diff)
                                    }
                                }
                            },
                            enabled = remainingHolidays > 0
                        ) {
                            Text(stringResource(R.string.dialog_ok_button_text))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(stringResource(R.string.dialog_dismiss_button_text))
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

        }
    }

}

@Composable
@Preview
fun PreviewHolidayScreen() {
    HolidayScreen()
}

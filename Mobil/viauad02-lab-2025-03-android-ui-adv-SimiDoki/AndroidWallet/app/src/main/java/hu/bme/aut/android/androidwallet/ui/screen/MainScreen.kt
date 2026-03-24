package hu.bme.aut.android.androidwallet.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.androidwallet.R
import hu.bme.aut.android.androidwallet.data.SalaryData
import hu.bme.aut.android.androidwallet.ui.common.SalaryCard
import hu.bme.aut.android.androidwallet.ui.common.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val salaryItems = remember { mutableStateListOf<SalaryData>() }

    var isIncome by remember { mutableStateOf(false) }
    var item by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val totalSum = salaryItems.sumOf {
        if (it.isIncome) it.price.toInt()
        else -it.price.toInt()
    }


    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {

            TopBar(
                title = stringResource(id = R.string.app_name),
                icon = Icons.Default.Clear,
                onIconClick = {
                    salaryItems.clear()
                },
                totalSum,
                onDeleteExpenses = { salaryItems.removeAll { !it.isIncome } },
                onDeleteIncomes = { salaryItems.removeAll { it.isIncome } },
                onDeleteAll = { salaryItems.clear() },

            )

        },
        //HT8QHM
        floatingActionButton = {
            LargeFloatingActionButton(
                shape = CircleShape,
                onClick = {
                    if (item.isNotEmpty() && price.isNotEmpty()) {
                        salaryItems += SalaryData(isIncome, item, price)
                    } else {

                        Toast.makeText(
                            context,
                            "",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
            {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "save button"
                )
            }
        },
        //HT8QHM
        content =
            { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (salaryItems.size == 0) {
                        Text(
                            text = stringResource(R.string.label_empty_list),
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                        )
                    } else
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(salaryItems.size) {
                                SalaryCard(
                                    isIncome = salaryItems[it].isIncome,
                                    item = salaryItems[it].item,
                                    price = "${salaryItems[it].price} Ft",
                                    onDelete = { salaryItems.remove(salaryItems[it])},
                                )
                                if (salaryItems.size - 1 > it) {
                                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                                }
                            }
                        }

                }
            },

        bottomBar = {
            BottomAppBar {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    IconToggleButton(
                        modifier = Modifier.size(64.dp),
                        checked = isIncome,
                        onCheckedChange = { isIncome = !isIncome },
                    ) {
                        Image(
                            modifier = Modifier.size(64.dp),
                            painter = painterResource(id = if (isIncome) R.drawable.ic_income else R.drawable.ic_expense),
                            contentDescription = "expense/income button"
                        )
                    }
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.item)) },
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(2f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        value = item,
                        onValueChange = {
                            item = it
                        }
                    )
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.price)) },
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = price,
                        onValueChange = {
                            price = it
                        }
                    )
                }
            }
        }

    )
}

@Composable
@Preview
fun PreviewMainScreen() {
    MainScreen()
}

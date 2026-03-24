package hu.bme.aut.android.androidwallet.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.androidwallet.R
import hu.bme.aut.android.androidwallet.data.SalaryData


@OptIn(ExperimentalMaterial3Api::class)
@Composable //HT8QHM
fun TopBar(title: String, icon: ImageVector, onIconClick: () -> Unit, totalSum: Int = 0,onDeleteExpenses: () -> Unit={},  onDeleteIncomes: () -> Unit={}, onDeleteAll: () -> Unit={}) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(

        title = {
                if(totalSum != 0){
                    Row{
                        Text(text = title)
                        Spacer(modifier = Modifier.width(80.dp))
                        Text(text = stringResource(R.string.sum)+ ": $totalSum")
                    }
                }
                else{
                    Text(text = title)
                }
        },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(imageVector = icon, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete Expenses") },
                    onClick = {
                        onDeleteExpenses()
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete Incomes") },
                    onClick = {
                        onDeleteIncomes()
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete All") },
                    onClick = {
                        onDeleteAll()
                        menuExpanded = false
                    }
                )
            }
        },
        /*actions = {
            IconButton(onClick = onIconClick) {
                Icon(imageVector = icon, contentDescription = "Delete", tint = Color.Red)
            }
        },*/
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
    )
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PreviewTopBar() {
    TopBar(title = "AndroidWallet", icon = Icons.Default.Clear,onIconClick = {},totalSum=0)
}

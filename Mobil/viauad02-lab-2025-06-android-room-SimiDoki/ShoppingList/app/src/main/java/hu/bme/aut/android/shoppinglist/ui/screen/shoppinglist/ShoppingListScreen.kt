package hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist.components.ShoppingItemDialog
import hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist.components.ShoppingListTopBar
import hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist.components.UIShoppingItem
import kotlin.random.Random


@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = viewModel(factory = ShoppingListViewModel.Factory)
){

    var isDialogOpen by remember { mutableStateOf(false) }

    val list = viewModel.shoppingItemList.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier,
        topBar = {
            ShoppingListTopBar(onDeleteAll = {
                    for (item in viewModel.shoppingItemList.value) {
                        viewModel.delete(item)
                    }
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    isDialogOpen = true
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(list, key = { item -> item.id!! }) {

                UIShoppingItem(
                    shoppingItem = it,
                    onCheckBoxClick = { shoppingItem ->
                        viewModel.update(shoppingItem)
                    },
                    //HT8QHM
                    onDeleteIconClick = { shoppingItem ->
                        viewModel.delete(shoppingItem)
                    },
                    onEditIconClick = {
                        /*TODO*/
                    }
                )
                if (list.indexOf(it) < list.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
    if (isDialogOpen) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            ShoppingItemDialog(
                onDismissRequest = { isDialogOpen = false },
                onSaveClick = { newShoppingItem ->
                    viewModel.insert(newShoppingItem)
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    ShoppingListScreen()
}

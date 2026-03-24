package hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingItemDialog(
    modifier : Modifier = Modifier,
    shoppingItem: ShoppingItem? = null,
    onDismissRequest: () -> Unit = {},
    onSaveClick: (ShoppingItem) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf(shoppingItem?.name ?: "") }
    var description by remember { mutableStateOf(shoppingItem?.description ?: "") }
    var price by remember { mutableStateOf(shoppingItem?.estimatedPrice?.toString() ?: "") }
    var category by remember {
        mutableStateOf(
            shoppingItem?.category ?: ShoppingItem.Category.FOOD
        )
    }
    var isBought by remember { mutableStateOf(shoppingItem?.isBought ?: false) }

    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(name.isEmpty()) }
    val categoryOptions = ShoppingItem.Category.entries.toList()

    Column(
        modifier = modifier
            .background(Color.White)
    ) {
        //Title of the Dialog Window
        Text(
            text =
            if (shoppingItem?.id == null)
                stringResource(id = R.string.add_shopping_item)
            else
                stringResource(id = R.string.edit_shopping_item),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        //Name of the item
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = name,
            label = { Text(text = stringResource(R.string.label_name)) },
            onValueChange = {
                name = it
                isNameError = it.isEmpty()
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = isNameError
        )

        //Price of the item
        OutlinedTextField(
            value = price,
            label = { Text(text = stringResource(R.string.label_estimated_price)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onValueChange = { price = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        //Description of the item
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = description,
            label = { Text(text = stringResource(R.string.label_description)) },
            onValueChange = { description = it },
            maxLines = 2
        )

        //Dropdown Menu for the category of the item
        ExposedDropdownMenuBox(
            expanded = isCategoryDropdownExpanded,
            onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded }) {

            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = getCategoryTextByCategory(category = category),
                label = {
                    Text(text = stringResource(id = R.string.label_category))
                },
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded)
                })

            ExposedDropdownMenu(
                expanded = isCategoryDropdownExpanded,
                onDismissRequest = { isCategoryDropdownExpanded = false }) {
                categoryOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            category = option
                            isCategoryDropdownExpanded = false
                        },
                        text = {
                            Text(
                                text = getCategoryTextByCategory(option)
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        //Row for the checkbox if the item is already bought
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isBought,
                onCheckedChange = {
                    isBought = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                )
            )
            Text(text = stringResource(R.string.label_already_purchased))
        }

        //Row for the buttons
        Row( //Row for the buttons
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = {
                    onDismissRequest()
                },
                shape = RectangleShape
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (!isNameError) {
                        coroutineScope.launch {
                            onDismissRequest()
                            onSaveClick(
                                ShoppingItem(
                                    id = shoppingItem?.id,
                                    name = name,
                                    description = description,
                                    estimatedPrice = if (price.isNotEmpty()) price.toInt() else 0,
                                    category = category,
                                    isBought = isBought
                                )
                            )
                        }
                    }
                },
                shape = RectangleShape
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
private fun getCategoryTextByCategory(category: ShoppingItem.Category) =
    when (category) {
        ShoppingItem.Category.ELECTRONIC -> stringResource(id = R.string.category_electronics)
        ShoppingItem.Category.BOOK -> stringResource(id = R.string.category_book)
        else -> stringResource(id = R.string.category_food)
    }


@Preview
@Composable
fun NewShoppingItemDialogPreview() {
    ShoppingItemDialog(onSaveClick = {})
}

@Preview
@Composable
fun EditShoppingItemDialogPreview() {
    ShoppingItemDialog(
        shoppingItem = ShoppingItem(
            name = "name",
            description = "description",
            estimatedPrice = 500,
            category = ShoppingItem.Category.BOOK,
            isBought = true
        ),
        onSaveClick = {}
    )
}

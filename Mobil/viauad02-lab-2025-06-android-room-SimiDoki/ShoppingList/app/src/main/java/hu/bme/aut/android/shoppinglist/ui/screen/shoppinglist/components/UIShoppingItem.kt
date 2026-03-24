package hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem

@Composable
fun UIShoppingItem(
    shoppingItem: ShoppingItem,
    onCheckBoxClick: (ShoppingItem) -> Unit,
    onDeleteIconClick: (ShoppingItem) -> Unit,
    onEditIconClick: (ShoppingItem) -> Unit
) {
    var isChecked by remember { mutableStateOf(shoppingItem.isBought) }
    var isInteractionPanelExpanded by remember { mutableStateOf(false) }
    val image = when (shoppingItem.category) {
        ShoppingItem.Category.FOOD -> R.drawable.groceries
        ShoppingItem.Category.ELECTRONIC -> R.drawable.lightning
        ShoppingItem.Category.BOOK -> R.drawable.open_book
        else -> R.drawable.groceries
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { isInteractionPanelExpanded = !isInteractionPanelExpanded }),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(80.dp),
            painter = painterResource(image),
            contentDescription = "image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = shoppingItem.name,
                fontSize = 20.sp,
                maxLines = 1
            )
            Text(
                text = shoppingItem.description,
                maxLines = 2,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "${shoppingItem.estimatedPrice} Ft",
                color = Color.DarkGray,
                maxLines = 1
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Checkbox(
                checked = shoppingItem.isBought,
                onCheckedChange = {
                    isChecked = it

                    onCheckBoxClick(shoppingItem.copy(isBought = isChecked))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    uncheckedColor = Color.Red,
                    checkmarkColor = Color.White
                ),
            )
            Text(
                text = if (shoppingItem.isBought) stringResource(R.string.label_purchased) else stringResource(R.string.label_not_purchased),
                textAlign = TextAlign.Center
            )
        }
        if (isInteractionPanelExpanded)
            Column() {
                IconButton(
                    modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary),
                    onClick = {
                        onDeleteIconClick(shoppingItem)
                        isInteractionPanelExpanded = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary),
                    onClick = {
                        /*TODO*/
                        isInteractionPanelExpanded = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemShoppingItemPurchasedPreview() {
    UIShoppingItem(
        shoppingItem = ShoppingItem(
            name = "LongItemName",
            description = "description",
            estimatedPrice = 500,
            category = ShoppingItem.Category.BOOK,
            isBought = true
        ),
        onDeleteIconClick = {},
        onCheckBoxClick = {},
        onEditIconClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ItemShoppingItemNotPurchasedPreview() {
    UIShoppingItem(
        shoppingItem = ShoppingItem(
            name = "LongItemName",
            description = "description description description description description",
            estimatedPrice = 500,
            category = ShoppingItem.Category.ELECTRONIC,
            isBought = false
        ),
        onDeleteIconClick = {},
        onCheckBoxClick = {},
        onEditIconClick = {}
    )
}

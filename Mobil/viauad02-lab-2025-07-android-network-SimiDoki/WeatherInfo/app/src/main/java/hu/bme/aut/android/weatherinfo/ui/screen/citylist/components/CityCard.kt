package hu.bme.aut.android.weatherinfo.ui.screen.citylist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.weatherinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityCard(
    city: String,
    onCityClick: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
        if (it == SwipeToDismissBoxValue.EndToStart) {
            onDelete(city)
        }
        return@rememberSwipeToDismissBoxState true
    },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        SwipeToDismissBox(
            enableDismissFromStartToEnd = false,
            state = dismissState,
            backgroundContent = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Color.Red
                                else -> MaterialTheme.colorScheme.background
                            }
                        )
                        .padding(12.dp, 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        ) {
            ListItem(
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                headlineContent = {
                    Text(
                        text = city,
                        fontSize = 24.sp
                    )

                },
                leadingContent = {
                    Icon(
                        modifier = Modifier
                            .size(64.dp),
                        painter = painterResource(id = R.drawable.ic_placeholder),
                        contentDescription = ""
                    )
                },
                modifier = Modifier.clickable(onClick = {
                    onCityClick(
                        city
                    )
                }
                )
            )
        }
    }

}

@Composable
@Preview
fun CityCardPreview() {
    CityCard(city = "Budapest", onCityClick = {}, onDelete = {})
}

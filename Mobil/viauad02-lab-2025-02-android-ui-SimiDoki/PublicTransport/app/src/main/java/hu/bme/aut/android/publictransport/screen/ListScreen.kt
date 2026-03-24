package hu.bme.aut.android.publictransport.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.publictransport.R
import androidx.compose.foundation.clickable

//HT8QHM
@Composable
fun ListScreen(
    onTransportClick: (s: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    Log.d("ListScreen", "Bike clicked")
                    onTransportClick("Bike")
                },
        ) {

            Image(
                painter = painterResource(id = R.drawable.bikes),
                contentDescription = "Bike Button",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Bike",
                fontSize = 36.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    Log.d("ListScreen", "Bus clicked")
                    onTransportClick("Bus")
                },
        ) {

            Image(
                painter = painterResource(id = R.drawable.bus),
                contentDescription = "Bus Button",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Bus",
                fontSize = 36.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    Log.d("ListScreen", "Train clicked")
                    onTransportClick("Train")
                },
        ) {

            Image(
                painter = painterResource(id = R.drawable.trains),
                contentDescription = "Train Button",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Train",
                fontSize = 36.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        //HT8QHM
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    Log.d("ListScreen", "Boat clicked")
                    onTransportClick("Boat")
                },
        ) {

            Image(
                painter = painterResource(id = R.drawable.boat),
                contentDescription = "Boat Button",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Boat",
                fontSize = 36.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

    }
}

@Preview
@Composable
fun PreviewListScreen() {
    ListScreen(onTransportClick = {})
}

package hu.bme.aut.android.weatherinfo.ui.screen.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.feature.weather.components.WeatherDataText
//import hu.bme.aut.android.weatherinfo.ui.screen.weather.components.WeatherDataText
import hu.bme.aut.android.weatherinfo.ui.screen.weather.state.WeatherScreenState
//HT8QHM
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    cityName: String,
    onNavigateBack: () -> Unit
) {
    val weatherViewModel: WeatherViewModel =
        viewModel(factory = WeatherViewModel.WeatherViewModelFactory(cityName), key = cityName)

    val state = weatherViewModel.state.collectAsStateWithLifecycle().value

    val cityName = weatherViewModel.cityName.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.label_weather_in, cityName)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        })
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            when (state) {
                is WeatherScreenState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is WeatherScreenState.Error -> {
                    Text(
                        text = state.error.message.toString()
                    )
                }

                is WeatherScreenState.Success -> {
                    Column {
                        Text(
                            text = state.weatherData?.weather?.get(0)?.description ?: "",
                            fontSize = 24.sp
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data("https://openweathermap.org/img/w/${state.weatherData?.weather?.first()?.icon}.png")
                                .crossfade(enable = true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.ic_placeholder),
                            modifier = Modifier
                                .size(320.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        WeatherDataText(
                            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
                            label = stringResource(id = R.string.label_temperature),
                            value = "${state.weatherData?.main?.temp} \u2103",
                            textSize = 24.sp
                        )
                        WeatherDataText(
                            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
                            label = stringResource(id = R.string.label_min_temperature),
                            value = "${state.weatherData?.main?.temp_min} \u2103",
                            textSize = 24.sp
                        )
                        WeatherDataText(
                            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
                            label = stringResource(id = R.string.label_max_temperature),
                            value = "${state.weatherData?.main?.temp_max} \u2103",
                            textSize = 24.sp
                        )
                        WeatherDataText(
                            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
                            label = stringResource(id = R.string.label_pressure),
                            value = "${state.weatherData?.main?.pressure} hPa",
                            textSize = 24.sp
                        )
                        WeatherDataText(
                            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
                            label = stringResource(id = R.string.label_humidity),
                            value = "${state.weatherData?.main?.humidity} %",
                            textSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun WeatherScreenPreview() {
    WeatherScreen(
        cityName = "Budapest",
        onNavigateBack = {})
}

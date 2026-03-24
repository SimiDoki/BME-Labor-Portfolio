package hu.bme.aut.android.weatherinfo.ui.screen.weather.state

import hu.bme.aut.android.weatherinfo.data.network.model.WeatherData

sealed class WeatherScreenState{
    data object Loading: WeatherScreenState()
    data class Error(val error: Throwable): WeatherScreenState()
    data class Success(val weatherData: WeatherData?): WeatherScreenState()
}

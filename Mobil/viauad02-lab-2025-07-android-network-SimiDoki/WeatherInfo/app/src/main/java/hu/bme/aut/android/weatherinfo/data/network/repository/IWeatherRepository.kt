package hu.bme.aut.android.weatherinfo.data.network.repository

import hu.bme.aut.android.weatherinfo.data.network.model.WeatherData
import retrofit2.Call

interface IWeatherRepository {
    suspend fun getWeather(city: String?): Call<WeatherData?>?
    suspend fun deleteCity(city: String)
}

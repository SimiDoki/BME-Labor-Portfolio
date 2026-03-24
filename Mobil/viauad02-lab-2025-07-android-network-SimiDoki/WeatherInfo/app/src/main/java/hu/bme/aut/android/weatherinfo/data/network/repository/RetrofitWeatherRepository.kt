package hu.bme.aut.android.weatherinfo.data.network.repository

import android.provider.UserDictionary.Words.APP_ID
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.android.weatherinfo.data.network.WeatherApi
import hu.bme.aut.android.weatherinfo.data.network.model.WeatherData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitWeatherRepository : IWeatherRepository {
    private val retrofit: Retrofit
    private val weatherApi: WeatherApi

    companion object {
        private const val SERVICE_URL = "https://api.openweathermap.org"
        private const val APP_ID = "c555f95527c12fa14830f58d327e7052"
    }
    //HT8QHM
    init {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }
    override suspend fun deleteCity(city: String) { }

    override suspend fun getWeather(city: String?): Call<WeatherData?>? {
        return weatherApi.getWeather(city, "metric", APP_ID)
    }
}

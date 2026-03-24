package hu.bme.aut.android.weatherinfo

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.weatherinfo.data.local.db.WeatherDatabase
import hu.bme.aut.android.weatherinfo.data.local.repository.ICityRepository
import hu.bme.aut.android.weatherinfo.data.local.repository.RoomCityRepository
import hu.bme.aut.android.weatherinfo.data.network.repository.IWeatherRepository
import hu.bme.aut.android.weatherinfo.data.network.repository.RetrofitWeatherRepository

class WeatherInfoApplication : Application() {

    companion object {
        lateinit var weatherRepository: IWeatherRepository
        lateinit var cityRepository: ICityRepository

        private lateinit var db: WeatherDatabase
    }

    override fun onCreate() {
        super.onCreate()

        weatherRepository = RetrofitWeatherRepository()

        db = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weather-database"
        ).build()

        cityRepository = RoomCityRepository(db.cityDao())
    }
}

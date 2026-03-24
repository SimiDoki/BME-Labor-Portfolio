package hu.bme.aut.android.weatherinfo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.weatherinfo.data.local.db.dao.CityDao
import hu.bme.aut.android.weatherinfo.domain.model.City
//HT8QHM
@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}

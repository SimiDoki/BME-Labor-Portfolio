package hu.bme.aut.android.weatherinfo.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.weatherinfo.domain.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAllCities(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City)

    @Query("DELETE FROM city WHERE name = :cityName")
    suspend fun deleteCityByName(cityName: String)
}

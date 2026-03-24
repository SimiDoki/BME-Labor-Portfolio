package hu.bme.aut.android.weatherinfo.data.local.repository

import hu.bme.aut.android.weatherinfo.data.local.db.dao.CityDao
import hu.bme.aut.android.weatherinfo.domain.model.City
import kotlinx.coroutines.flow.Flow

class RoomCityRepository(private val cityDao: CityDao) : ICityRepository {
    override suspend fun getAllCities(): Flow<List<City>> {
        return cityDao.getAllCities()
    }

    override suspend fun addCityByName(cityName: String) {
        cityDao.insertCity(City(name = cityName))
    }

    override suspend fun deleteCity(cityName: String) {
        cityDao.deleteCityByName(cityName)
    }
}

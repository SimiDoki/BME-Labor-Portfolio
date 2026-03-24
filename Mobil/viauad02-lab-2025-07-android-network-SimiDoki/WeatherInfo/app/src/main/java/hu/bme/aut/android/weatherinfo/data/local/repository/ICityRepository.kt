package hu.bme.aut.android.weatherinfo.data.local.repository

import hu.bme.aut.android.weatherinfo.domain.model.City
import kotlinx.coroutines.flow.Flow

interface ICityRepository {

    suspend fun getAllCities(): Flow<List<City>>
    suspend fun addCityByName(cityName: String)
    suspend fun deleteCity(cityName: String)
}

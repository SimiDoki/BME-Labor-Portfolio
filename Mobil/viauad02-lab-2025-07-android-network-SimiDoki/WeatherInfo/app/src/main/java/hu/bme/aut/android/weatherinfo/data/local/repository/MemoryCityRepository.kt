package hu.bme.aut.android.weatherinfo.data.local.repository

import androidx.compose.runtime.mutableStateListOf
import hu.bme.aut.android.weatherinfo.domain.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemoryCityRepository : ICityRepository {
    private var nextId=3

    private var cities = mutableStateListOf(
        City(id = 0, name = "Budapest"),
        City(id = 1, name = "London"),
        City(id = 2, name = "Berlin")
    )

    override suspend fun getAllCities(): Flow<List<City>> = flow {
        emit(cities)
    }

    override suspend fun addCityByName(cityName: String) {
        cities.add(City(id = nextId++, name = cityName))
    }

    override suspend fun deleteCity(cityName: String) {
        cities.removeIf { it.name == cityName }
    }

}

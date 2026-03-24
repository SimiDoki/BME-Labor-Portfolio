package hu.bme.aut.android.weatherinfo.ui.screen.citylist.state

import hu.bme.aut.android.weatherinfo.domain.model.City


sealed class CityListScreenState {
    data object Loading : CityListScreenState()
    data class Error(val error: Throwable) : CityListScreenState()
    data class Result(val cityList: List<City>) : CityListScreenState()
}

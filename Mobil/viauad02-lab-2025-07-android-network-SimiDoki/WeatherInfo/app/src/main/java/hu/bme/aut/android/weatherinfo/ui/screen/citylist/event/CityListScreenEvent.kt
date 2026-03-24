package hu.bme.aut.android.weatherinfo.ui.screen.citylist.event

sealed class CityListScreenEvent {
    data class CityCreated(val city: String) : CityListScreenEvent()
    data class DeleteCity(val city: String) : CityListScreenEvent()
}

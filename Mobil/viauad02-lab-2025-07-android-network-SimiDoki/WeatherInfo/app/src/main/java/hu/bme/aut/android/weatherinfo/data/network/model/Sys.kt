package hu.bme.aut.android.weatherinfo.data.network.model

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
package hu.bme.aut.android.weatherinfo.ui.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.weatherinfo.WeatherInfoApplication
import hu.bme.aut.android.weatherinfo.data.network.model.WeatherData
import hu.bme.aut.android.weatherinfo.data.network.repository.IWeatherRepository
import hu.bme.aut.android.weatherinfo.ui.screen.weather.state.WeatherScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.jvm.java

class WeatherViewModel(
    private val cityNameArg: String,
    private val weatherRepository: IWeatherRepository
) : ViewModel() {
    private var _state = MutableStateFlow<WeatherScreenState>(WeatherScreenState.Loading)
    val state = _state.asStateFlow()

    private var _cityName = MutableStateFlow<String>(cityNameArg)
    val cityName = _cityName.asStateFlow()

    init {
        getWeather(cityName.value)
    }

    private fun getWeather(cityName: String) {
        viewModelScope.launch {
            _state.value = WeatherScreenState.Loading
            try {
                weatherRepository.getWeather(cityName)
                    ?.enqueue(object : Callback<WeatherData?> {
                        override fun onResponse(
                            call: Call<WeatherData?>,
                            response: Response<WeatherData?>
                        ) {
                            if (response.isSuccessful) {
                                _state.tryEmit(WeatherScreenState.Success(response.body()))
                            }
                        }

                        override fun onFailure(
                            call: Call<WeatherData?>,
                            t: Throwable
                        ) {
                            t.printStackTrace()
                            _state.value = WeatherScreenState.Error(t)
                        }
                    })
            } catch (e: Exception) {
                _state.value = WeatherScreenState.Error(e)
            }
        }
    }

    class WeatherViewModelFactory(private val cityName: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val weatherRepository = WeatherInfoApplication.weatherRepository
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(
                    cityNameArg = cityName,
                    weatherRepository = weatherRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}

/*
ViewModel huolehtii:

käyttäjän syöttämästä kaupungista
API-kutsun tekemisestä coroutineilla
virhetilojen käsittelystä
UI-tilan tarjoamisesta Composelle
ViewModelissa on:

UI-tilan data class
funktio kaupungin päivittämiseen
funktio sään hakemiseen
error-handling
*/

package com.weather.week5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.week5.data.model.WeatherResponse
import com.weather.week5.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _city = MutableStateFlow("Lexington")
    val city: StateFlow<String> = _city

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _unit = MutableStateFlow("C")
    val unit: StateFlow<String> = _unit

    fun onUnitChanged(newUnit: String) {
        _unit.value = newUnit
    }

    fun convertTemp(tempCelsius: Double): Double {
        return if (_unit.value == "C") tempCelsius
        else tempCelsius * 9 / 5 + 32
    }



    init {
        fetchWeather()
    }

    fun onCityChanged(newCity: String) {
        _city.value = newCity
        _error.value = null
        _weather.value = null
    }

    fun fetchWeather() {
        val cityName = _city.value.trim()
        if (cityName.isEmpty()) {
            _error.value = "Please enter a city."
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val result = RetrofitInstance.api.getWeatherByCity(cityName)

                _weather.value = result
            } catch (e: Exception) {
                _error.value = "Could not load weather."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

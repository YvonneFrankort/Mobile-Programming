package com.weather.week5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weather.week5.data.local.AppDatabase
import com.weather.week5.data.model.WeatherEntity
import com.weather.week5.data.repository.WeatherRepository
import com.weather.week5.data.remote.WeatherApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository

    init {
        // Create DAO and API
        val dao = AppDatabase.getDatabase(application).weatherDao()
        val api = WeatherApi.create()  // Retrofit API
        repository = WeatherRepository(api, dao)
    }

    // Selected city state
    private val _selectedCity = MutableStateFlow<String?>(null)
    val selectedCity: StateFlow<String?> = _selectedCity

    // All cities from Room
    val allCities: StateFlow<List<WeatherEntity>> =
        repository.getAllCities()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Weather for selected city from Room
    val weatherForSelectedCity: StateFlow<WeatherEntity?> =
        _selectedCity
            .flatMapLatest { city ->
                if (city == null) flowOf(null)
                else repository.getWeatherFromDb(city)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _unit = MutableStateFlow("C")
    val unit: StateFlow<String> = _unit

    fun onUnitChanged(newUnit: String) {
        _unit.value = newUnit
    }

    fun convertTemp(tempCelsius: Double): Double =
        if (_unit.value == "C") tempCelsius else tempCelsius * 9 / 5 + 32

    fun selectCity(city: String) {
        _selectedCity.value = city
        refreshWeather(city)
    }

    fun refreshWeather(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val current = repository.getWeatherFromDb(city).firstOrNull()
                val now = System.currentTimeMillis()
                val thirtyMinutes = 30 * 60 * 1000

                // Only refresh if older than 30 minutes
                if (current != null && now - current.timestamp < thirtyMinutes) {
                    // Data is fresh; nothing to do
                    return@launch
                }

                // Fetch real data from API → Room
                repository.fetchWeatherFromApi(city)

            } catch (e: Exception) {
                _error.value = "Could not refresh weather."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCity(city: String) {
        viewModelScope.launch {
            repository.deleteCity(city)
            if (_selectedCity.value == city) _selectedCity.value = null
        }
    }

    fun addCity(city: String) {
        if (city.isBlank()) {
            _error.value = "City cannot be empty."
            return
        }
        val exists = allCities.value.any { it.cityName.equals(city, ignoreCase = true) }
        if (exists) {
            _error.value = "City already exists."
            return
        }

        viewModelScope.launch {
            // Insert a new city (fetch from API)
            repository.fetchWeatherFromApi(city)
            _selectedCity.value = city
        }
    }
}
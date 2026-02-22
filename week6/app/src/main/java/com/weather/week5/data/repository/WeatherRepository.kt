package com.weather.week5.data.repository

import com.weather.week5.data.local.WeatherDao
import com.weather.week5.data.model.WeatherEntity
import com.weather.week5.data.remote.WeatherApi
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {

    // Room flows
    fun getWeatherFromDb(city: String): Flow<WeatherEntity?> = dao.getWeatherByCity(city)
    fun getAllCities(): Flow<List<WeatherEntity>> = dao.getAllCities()

    // CRUD for Room
    suspend fun insertWeather(weather: WeatherEntity) = dao.insertWeather(weather)
    suspend fun deleteCity(city: String) = dao.deleteCity(city)

    // Fetch data from API, convert to WeatherEntity, store in Room
    suspend fun fetchWeatherFromApi(city: String): WeatherEntity {
        val response = api.getWeatherByCity(city)

        // Convert sunrise/sunset to local time
        val sunriseLocal = formatUnixToLocalTime(response.sys.sunrise, response.timezone)
        val sunsetLocal = formatUnixToLocalTime(response.sys.sunset, response.timezone)

        // Convert API → Room entity
        val entity = WeatherEntity(
            cityName = response.name,
            temp = response.main.temp,
            feelsLike = response.main.feelsLike,
            humidity = response.main.humidity,
            windSpeed = response.wind.speed,
            description = response.weather.firstOrNull()?.description ?: "",
            icon = response.weather.firstOrNull()?.icon ?: "",
            sunrise = sunriseLocal,
            sunset = sunsetLocal,
            timestamp = System.currentTimeMillis()
        )

        // Save to Room
        dao.insertWeather(entity)

        return entity
    }

    // Helper for sunrise/sunset conversion
    private fun formatUnixToLocalTime(unix: Long, timezoneOffset: Int): String {
        val millis = (unix + timezoneOffset) * 1000L
        val date = java.util.Date(millis)
        val formatter = java.text.SimpleDateFormat("HH:mm")
        formatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
        return formatter.format(date)
    }
}
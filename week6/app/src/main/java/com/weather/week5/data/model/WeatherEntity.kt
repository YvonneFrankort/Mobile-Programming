package com.weather.week5.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val cityName: String,        // City name as unique key

    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,

    val description: String,
    val icon: String,

    val sunrise: String,
    val sunset: String,

    val timestamp: Long          // When this data was saved
)

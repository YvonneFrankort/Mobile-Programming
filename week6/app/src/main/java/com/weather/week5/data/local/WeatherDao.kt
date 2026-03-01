package com.weather.week5.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.week5.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table WHERE cityName = :city LIMIT 1")
    fun getWeatherByCity(city: String): Flow<WeatherEntity?>

    @Query("SELECT * FROM weather_table")
    fun getAllCities(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM weather_table WHERE cityName = :city")
    suspend fun deleteCity(city: String)

}

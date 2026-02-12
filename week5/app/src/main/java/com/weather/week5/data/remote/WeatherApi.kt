package com.weather.week5.data.remote

//Sisältää Retrofit-rajapinnan (interface).
//Tänne tulee yksi funktio, joka tekee API-kutsun (city → WeatherResponse).

import com.weather.week5.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.weather.week5.BuildConfig




interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

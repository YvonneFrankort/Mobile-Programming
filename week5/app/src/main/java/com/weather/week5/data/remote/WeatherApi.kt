package com.weather.week5.data.remote


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

    companion object {
        fun create(): WeatherApi {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherApi::class.java)
        }
    }
}

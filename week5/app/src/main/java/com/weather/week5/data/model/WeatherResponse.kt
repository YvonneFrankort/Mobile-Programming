//Vastaa OpenWeatherin JSON-rakennetta.
//Luo dataluokat samoilla nimillä kuin API palauttaa.


package com.weather.week5.data.model
import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    val name: String,        // City
    val main: Main,          // temperature
    val wind: Wind,
    val sys: Sys,             // sunrise/sunset
    val timezone: Int,
    val weather: List<Weather> // description
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)


data class Weather(
    val id: Int,
    val main: String,
    val description: String,  // Weather description
    val icon: String,
)

data class Wind(
    val speed: Double // Wind speed (m/s)

)
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)


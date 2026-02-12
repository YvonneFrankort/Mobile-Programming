package com.weather.week5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.weather.week5.data.model.WeatherResponse
import androidx.compose.foundation.background
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WeatherResultSection(
    weather: WeatherResponse,
    unit: String
) {
    val unitSymbol = if (unit == "F") "°F" else "°C"

    fun convert(temp: Double): Double =
        if (unit == "F") temp * 9 / 5 + 32 else temp

    fun formatTime(timestamp: Long, timezoneOffset: Int): String {
        val date = java.util.Date((timestamp + timezoneOffset) * 1000)
        val format = java.text.SimpleDateFormat("HH:mm")
        format.timeZone = java.util.TimeZone.getTimeZone("UTC")
        return format.format(date)
    }

    val description = weather.weather.firstOrNull()?.description
        ?.replaceFirstChar { it.uppercase() }
        ?: ""

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {

        val iconCode = weather.weather.firstOrNull()?.icon ?: "01d"
        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

        Image(
            painter = rememberAsyncImagePainter(iconUrl),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "${String.format("%.1f", convert(weather.main.temp))}$unitSymbol",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        Spacer(Modifier.height(20.dp))


        Text("Feels like: ${String.format("%.1f", convert(weather.main.feelsLike))}$unitSymbol")
        Text("Min: ${String.format("%.1f", convert(weather.main.tempMin))}$unitSymbol")
        Text("Max: ${String.format("%.1f", convert(weather.main.tempMax))}$unitSymbol")
        Text("Humidity: ${weather.main.humidity}%")
        Text("Wind: ${weather.wind.speed} m/s")

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        Spacer(Modifier.height(20.dp))

        Text("Sunrise: ${formatTime(weather.sys.sunrise, weather.timezone)}")
        Text("Sunset: ${formatTime(weather.sys.sunset, weather.timezone)}")
    }
}

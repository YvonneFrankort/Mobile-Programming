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
import com.weather.week5.data.model.WeatherEntity
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WeatherResultSection(
    weather: WeatherEntity,
    unit: String,
    convertTemp: (Double) -> Double
) {
    val unitSymbol = if (unit == "F") "°F" else "°C"

    val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(iconUrl),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "${String.format("%.1f", convertTemp(weather.temp))}$unitSymbol",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = weather.description.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        Spacer(Modifier.height(20.dp))

        Text("Feels like: ${String.format("%.1f", convertTemp(weather.feelsLike))}$unitSymbol")
        Text("Humidity: ${weather.humidity}%")
        Text("Wind: ${weather.windSpeed} m/s")

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        Spacer(Modifier.height(20.dp))

        Text("Sunrise: ${weather.sunrise}")
        Text("Sunset: ${weather.sunset}")
    }
}

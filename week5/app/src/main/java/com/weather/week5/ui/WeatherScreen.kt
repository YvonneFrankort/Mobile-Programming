package com.weather.week5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.week5.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val city by viewModel.city.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val unit by viewModel.unit.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = viewModel::onCityChanged,
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { viewModel.fetchWeather() },
            enabled = city.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get weather")
        }

        // Temperature unit selector
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = unit == "C",
                    onClick = { viewModel.onUnitChanged("C") }
                )
                Text("°C", modifier = Modifier.padding(start = 4.dp))
            }

            Spacer(Modifier.width(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = unit == "F",
                    onClick = { viewModel.onUnitChanged("F") }
                )
                Text("°F", modifier = Modifier.padding(start = 4.dp))
            }
        }

        Spacer(Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        weather?.let { result ->
            WeatherResultSection(result, unit)
        }

    }
}

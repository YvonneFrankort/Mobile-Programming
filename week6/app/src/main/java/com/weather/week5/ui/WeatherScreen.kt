package com.weather.week5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.weather.week5.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val allCities by viewModel.allCities.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val weather by viewModel.weatherForSelectedCity.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val unit by viewModel.unit.collectAsState()

    var newCityInput by remember { mutableStateOf("") }

    LaunchedEffect(selectedCity) {
        selectedCity?.let { city ->
            viewModel.refreshWeather(city)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // make parent scrollable if needed
    ) {

        OutlinedTextField(
            value = newCityInput,
            onValueChange = { newCityInput = it },
            label = { Text("Add a city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.addCity(newCityInput)
                newCityInput = ""
            },
            enabled = newCityInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = unit == "C",
                onClick = { viewModel.onUnitChanged("C") }
            )
            Text("°C")
            Spacer(Modifier.width(16.dp))
            RadioButton(
                selected = unit == "F",
                onClick = { viewModel.onUnitChanged("F") }
            )
            Text("°F")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Saved cities:",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(allCities) { city ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            "https://openweathermap.org/img/wn/${city.icon}.png"
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )

                    Text(
                        text = city.cityName,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.selectCity(city.cityName) },
                        color = if (selectedCity == city.cityName)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(onClick = { viewModel.deleteCity(city.cityName) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete city",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        selectedCity?.let { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = city,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.refreshWeather(city) }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh weather"
                    )
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        weather?.let { entity ->
            WeatherResultSection(
                weather = entity,
                unit = unit,
                convertTemp = viewModel::convertTemp
            )
        }
    }
}
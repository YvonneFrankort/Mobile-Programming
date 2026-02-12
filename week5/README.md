# Mobile Programming with Native Technologies

## Week 5 Assignment – Weather App with Retrofit & Open Weather

### Overview

This week’s assignment was to build a simple weather application using:

* Jetpack Compose for UI
* Retrofit for network requests
* Coroutines for background work
* OpenWeather API for real weather data

The app allows the user to enter a city name, fetch the current weather, switch between Celsius and Fahrenheit, and view sunrise/sunset times adjusted to the correct timezone.


### New Features

#### Weather Search

* User enters a city name
* App fetches current weather from OpenWeather API
* Displays temperature, description, humidity, wind, min/max, sunrise, sunset

#### Temperature Unit Toggle
* Buttons to switch between °C and °F
* UI updates instantly
* Conversion is done in the UI layer

#### Weather Icons
* Real OpenWeather icons loaded with Coil
* Matches the weather conditions (e.g., clear, clouds, rain)

#### Timezone‑Correct Sunrise & Sunset
* OpenWeather gives timestamps in UTC
* App adds the city’s timezone offset
* Times are formatted cleanly (HH:mm)

## What Retrofit Does
Retrofit handles all HTTP requests to the OpenWeather API.

#### How it works in this app
* Sends a GET request to the weather endpoint
* Receives a JSON response
* Converts JSON into Kotlin data classes automatically

## How JSON coverts into a data class
* through GsonConverterFactory.create()
* Retrofit receives JSON
* Gson converts it into our data classes (WeatherResponse, Main, Wind, etc.)
* No manual parsing needed

## How Coroutines Work Here
Coroutines allow the app to run network requests off the main thread.

In this app:
* API call runs inside viewModelScope.launch
* The UI stays responsive
* When the data arrives, the ViewModel updates the UI state
* Compose automatically re-renders the screen
* This keeps the app smooth and avoids blocking the UI.

## How UI State Works
The app uses a simple WeatherUiState object inside the ViewModel.

#### ViewModel responsibilities
* Holds the current weather data
* Calls the API
* Updates the UI state when data arrives

#### Compose responsibilities
* Observes the state
* Automatically updates the UI when the state changes

## How the API Key Is Stored
* The OpenWeather API key is not hardcoded.
* The key is placed in local.properties
* Gradle exposes it through BuildConfig
* Retrofit reads it from BuildConfig.OPENWEATHER_API_KEY
This keeps the key out of version control.

## APK
The debug APK is included in the week5/ folder of this repository.

## Demo video
https://youtube.com/shorts/fabefcYOWQ4?feature=share

## Screenshot
<img width="337" height="640" alt="wetaher_app" src="https://github.com/user-attachments/assets/21766418-6f4e-4c93-9fc5-dcce4a3ddda7" />

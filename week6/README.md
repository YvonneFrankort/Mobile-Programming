# Mobile Programming with Native Technologies

## Week  Assignment – Weather App with Room

### Overview

This week’s assignment was to extend the Week 5 weather app by adding persistent storage using Room. The goal was to implement:

* Entity
* DAO
* RoomDatabase
* Repository
* ViewModel
* Flow/State

The app now stores weather data locally, loads it from Room, and only refreshes from the API when needed.


### Architecture

## Overview
UI (Compose)
   ↓ observes StateFlow
ViewModel
   ↓ calls suspend + Flow
Repository
   ↓ uses
DAO (Room)
   ↓ reads/writes
SQLite Database

## Structure
The project follows a clean MVVM architecture where the UI never accesses the database or API directly.

/data
   /model
      WeatherEntity.kt
   /local
      WeatherDao.kt
      AppDatabase.kt
   /remote
      WeatherApi.kt
   /repository
      WeatherRepository.kt

/viewmodel
   WeatherViewModel.kt

/ui
   WeatherScreen.kt
   WeatherResultSection.kt


#### What Room does
(Entity → DAO → Database → Repository → ViewModel → UI)

Room provides the persistent local storage layer of the app.
The data flow looks like this:

* # Entity
Defines the structure of a weather record stored in the database (city, temperature, humidity, wind, sunrise, sunset, timestamp, etc.)
* # DAO
Contains the SQL operations:
* getAllCities() 
* getWeatherByCity(city)
* insertWeather(entity)
* deleteCity(city)
* # RoomDatabase
Creates the SQLite database and exposes the DAO.
Implemented as a singleton using getDatabase.
* # Repository  
Handles data flow between Room and Retrofit. It:
* Reads weather data from Room (Flow)
* Fetches new weather from the API
* Converts sunrise/sunset to local time
* Saves updated weather into Room
* # ViewModel
* Collects DAO flows as StateFlow
* Exposes UI state (cities, selected city, weather, loading, error, unit)
* Implements caching logic
* Handles add/delete/select city actions
* Calls the repository when data needs refreshing
* # UI
* Observes ViewModel StateFlows
* Displays the list of saved cities from Room
* Shows weather details for the selected city
* Triggers ViewModel actions (add, delete, refresh)
* Automatically updates when Room data changes

#### How Data flowsthrough the app
1. The user adds or selects a city in the UI.
2. The UI calls the ViewModel.
3. The ViewModel reads the latest weather data from Room.
4. If the data is fresh (< 30 minutes old), it is shown immediately.
5. If the data is stale, the ViewModel asks the Repository to refresh it.
6. The Repository fetches new weather from the API, converts sunrise/sunset, and saves it to Room.
7. Room emits updated data via Flow.
8. The ViewModel updates its StateFlow.
9. Compose UI re-renders automatically

#### How does the catching logic work
The app implements a simple caching mechanism:
* Each weather entry includes a timestamp (System.currentTimeMillis).
* When a city is selected, the ViewModel checks how old the data is.
* If the data is less than 30 minutes old, the app uses the cached Room data.
* If the data is older than 30 minutes, the app:
- Fetches fresh weather from the API
- Converts sunrise/sunset to local time
- Saves the new entry into Room
- UI updates automatically
This reduces unnecessary API calls and fulfills the assignment’s caching requirement.

#### Demo Video (YouTube)
https://youtube.com/shorts/ShKCDcPHSjk?feature=share

## Screenshot
<img width="356" height="746" alt="Weather_Room" src="https://github.com/user-attachments/assets/e4670fa5-e05a-4762-ba0c-a3d2a560c1b9" />



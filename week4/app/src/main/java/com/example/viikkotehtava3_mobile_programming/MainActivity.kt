package com.example.viikkotehtava3_mobile_programming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkotehtava3_mobile_programming.navigation.ROUTE_HOME
import com.example.viikkotehtava3_mobile_programming.navigation.ROUTE_CALENDAR
import com.example.viikkotehtava3_mobile_programming.navigation.ROUTE_SETTINGS
import com.example.viikkotehtava3_mobile_programming.viewmodel.TaskViewModel
import com.example.viikkotehtava3_mobile_programming.view.HomeScreen
import com.example.viikkotehtava3_mobile_programming.view.CalendarScreen
import com.example.viikkotehtava3_mobile_programming.view.SettingsScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode = Setting.darkModeEnabled

            MaterialTheme(
                colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()
            ) {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {

                    composable(ROUTE_HOME) {
                        HomeScreen(
                            viewModel = taskViewModel,
                            onNavigateCalendar = { navController.navigate(ROUTE_CALENDAR) },
                            onNavigateSettings = { navController.navigate(ROUTE_SETTINGS) }
                        )
                    }

                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            viewModel = taskViewModel,
                            onNavigateHome = { navController.navigate(ROUTE_HOME) }
                        )
                    }

                    composable(ROUTE_SETTINGS) {
                        SettingsScreen(
                            onNavigateHome = { navController.navigate(ROUTE_HOME) }
                        )
                    }
                }
            }
        }
    }
}

package com.example.viikkotehtava1_mobile_programming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.viikkotehtava1_mobile_programming.ui.HomeScreen
import com.example.viikkotehtava1_mobile_programming.ui.theme.Viikkotehtava1_Mobile_ProgrammingTheme
import com.example.viikkotehtava1_mobile_programming.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikkotehtava1_Mobile_ProgrammingTheme {
                HomeScreen(viewModel = taskViewModel)
            }
        }
    }
}
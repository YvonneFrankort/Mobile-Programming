package com.example.viikkotehtava1_mobile_programming.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.viikkotehtava1_mobile_programming.ui.theme.Viikkotehtava1_Mobile_ProgrammingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikkotehtava1_Mobile_ProgrammingTheme {
                HomeScreen()
            }
        }
    }
}
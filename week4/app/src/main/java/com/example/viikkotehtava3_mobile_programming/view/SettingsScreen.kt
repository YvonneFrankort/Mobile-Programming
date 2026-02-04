package com.example.viikkotehtava3_mobile_programming.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import com.example.viikkotehtava3_mobile_programming.Setting


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark mode", modifier = Modifier.weight(1f))
            Switch(
                checked = Setting.darkModeEnabled,
                onCheckedChange = { Setting.darkModeEnabled = it }
            )
        }
    }
}

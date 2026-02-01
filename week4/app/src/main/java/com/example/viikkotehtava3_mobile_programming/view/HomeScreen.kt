package com.example.viikkotehtava3_mobile_programming.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import com.example.viikkotehtava3_mobile_programming.viewmodel.TaskViewModel
import com.example.viikkotehtava3_mobile_programming.domain.Task

@Composable
fun HomeScreen(viewModel: TaskViewModel) {

    val tasks by viewModel.tasks.collectAsState()


    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }


    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(modifier = Modifier.padding(20.dp)) {

        Text(
            "My Tasks",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            "Add a new task",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = newDescription,
                onValueChange = { newDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (newTitle.isNotBlank()) {
                        val task = Task(
                            id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                            title = newTitle,
                            description = newDescription,
                            priority = 1,
                            dueDate = "2026-09-01",
                            done = false
                        )
                        viewModel.addTask(task)
                        newTitle = ""
                        newDescription = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.showAllTasks() }) {
                Text("All")
            }
            Button(onClick = { viewModel.filterByDone(true) }) {
                Text("Completed")
            }
            Button(onClick = { viewModel.sortByDueDate() }) {
                Text("Sort by Date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(tasks) { task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF7F7F7)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                task.title,
                                color = if (task.done) Color(0xFF2E7D32) else Color.Black
                            )
                            if (task.description.isNotBlank()) {
                                Text(
                                    task.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Button(
                            onClick = { selectedTask = task },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1976D2),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Edit")
                        }

                        Button(
                            onClick = { viewModel.removeTask(task.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        if (selectedTask != null) {
            DetailDialog(
                task = selectedTask!!,
                onDismiss = { selectedTask = null },
                onSave = { updatedTask ->
                    viewModel.updateTask(updatedTask)
                    selectedTask = null
                },
                onDelete = {
                    viewModel.removeTask(selectedTask!!.id)
                    selectedTask = null
                }
            )
        }
    }
}
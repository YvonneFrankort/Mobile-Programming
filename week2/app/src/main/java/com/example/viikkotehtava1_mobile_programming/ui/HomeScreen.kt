package com.example.viikkotehtava1_mobile_programming.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import com.example.viikkotehtava1_mobile_programming.viewmodel.TaskViewModel
import com.example.viikkotehtava1_mobile_programming.domain.Task

@Composable
fun HomeScreen(viewModel: TaskViewModel) {

    val tasks = viewModel.tasks
    var newTask by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("My Tasks")

        Row {
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        val task = Task(
                            id = tasks.size + 1,
                            title = newTask,
                            description = "",
                            priority = 1,
                            dueDate = "2026-09-01",
                            done = false
                        )
                        viewModel.addTask(task)
                        newTask = ""
                    }
                },
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    Text(task.title,
                        color = if (task.done) Color(0xFF2E7D32) else Color.Black,
                        modifier = Modifier.weight(1f))
                    Button(onClick = { viewModel.removeTask(task.id) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

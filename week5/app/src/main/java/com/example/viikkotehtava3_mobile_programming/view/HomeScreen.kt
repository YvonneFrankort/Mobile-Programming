package com.example.viikkotehtava3_mobile_programming.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.viikkotehtava3_mobile_programming.viewmodel.TaskViewModel
import com.example.viikkotehtava3_mobile_programming.domain.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onNavigateCalendar: () -> Unit,
    onNavigateSettings: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    val isAddDialogVisible by viewModel.isAddDialogVisible.collectAsState()

    Column {

        TopAppBar(
            title = { Text("Task List") },
            actions = {
                IconButton(onClick = { viewModel.openAddDialog() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
                IconButton(onClick = onNavigateCalendar) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar")
                }
                IconButton(onClick = onNavigateSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.showAllTasks() }) { Text("All") }
            Button(onClick = { viewModel.filterByDone(true) }) { Text("Completed") }
            Button(onClick = { viewModel.sortByDueDate() }) { Text("Sort by Date") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(tasks) { task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(task.title)
                            if (task.description.isNotBlank()) {
                                Text(
                                    task.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )

                        Button(onClick = { viewModel.openEditDialog(task) }) {
                            Text("Edit")
                        }

                        Button(onClick = { viewModel.removeTask(task.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }

    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onDismiss = { viewModel.closeEditDialog() },
            onSave = { updatedTask -> viewModel.updateTask(updatedTask) },
            onDelete = {
                viewModel.removeTask(selectedTask!!.id)
                viewModel.closeEditDialog()
            }
        )
    }

    if (isAddDialogVisible) {
        AddDialog(
            onDismiss = { viewModel.closeAddDialog() },
            onSave = { newTask -> viewModel.addTask(newTask) },
            nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        )
    }
}

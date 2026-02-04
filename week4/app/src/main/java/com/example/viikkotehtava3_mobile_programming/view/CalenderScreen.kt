package com.example.viikkotehtava3_mobile_programming.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava3_mobile_programming.viewmodel.TaskViewModel
import com.example.viikkotehtava3_mobile_programming.domain.Task
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.Icons



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onNavigateHome: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()

    Column {

        // --- TOP BAR ---
        TopAppBar(
            title = { Text("Calendar") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val grouped = tasks.groupBy { it.dueDate }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            grouped.forEach { (date, tasksForDate) ->
                item {
                    Text(
                        text = "Date: $date",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(tasksForDate) { task ->
                    CalendarTaskItem(
                        task = task,
                        onToggleDone = { viewModel.toggleDone(task.id) },
                        onDelete = { viewModel.removeTask(task.id) },
                        onEdit = { viewModel.openEditDialog(task) }
                    )
                }
            }
        }
    }

    // --- EDIT DIALOG ---
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
}

@Composable
fun CalendarTaskItem(
    task: Task,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onEdit() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(task.title)

        Row {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() }
            )
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}


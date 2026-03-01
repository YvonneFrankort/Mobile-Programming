package com.example.viikkotehtava1_mobile_programming.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava1_mobile_programming.domain.*

@Composable
fun HomeScreen() {

    var tasks by remember { mutableStateOf(MyTask) }

    Column (modifier = Modifier.padding(16.dp)) {

        Text("My Tasks")

        Row {
            Button(onClick = {
                tasks = addTask(tasks, Task(6, "New Task", "Description", 1, "2026-09-02", false))
            }) {
                Text("Add Task")
            }
            Button(onClick = { tasks = filterByDone(tasks, true) }) {
                Text("Show Done")
            }
            Button(onClick = { tasks = sortByDueDate(tasks) }) {
                Text("Sort by Date")
            }
        }

        tasks.forEach { task ->
            Row {
                Text("${task.title}: ${task.description}")

                Button(onClick = { tasks = toggleDone(tasks, task.id) }) {
                    Text(if (task.done) "Mark Undone" else "Mark Done")
                }
            }
        }
    }
}
